package com.tcloud.im.gateway.websocket.server;

import com.alibaba.fastjson2.JSON;
import com.tcloud.idgenerator.handler.DistributedIdGenerator;
import com.tcloud.im.common.utils.NetUtil;
import com.tcloud.im.gateway.websocket.cache.ServerInstanceInfo;
import com.tcloud.im.gateway.websocket.config.WebSocketServerConfig;
import com.tcloud.im.gateway.websocket.handlers.AuthenticationHandler;
import com.tcloud.im.gateway.websocket.handlers.ChatFunctionHandlerSelector;
import com.tcloud.register.domain.ServerInfo;
import com.tcloud.register.handler.server.ServerRegister;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
import io.netty.handler.timeout.IdleStateHandler;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * IM Netty 服务
 *
 * @author Anker
 */
@Slf4j
@Configuration
@ConditionalOnBean(WebSocketServerConfig.class)
public class WebSocketServer implements ApplicationListener<ContextClosedEvent> {

    private EventLoopGroup bossGroup;

    private EventLoopGroup workGroup;

    private ServerBootstrap bootstrap;

    @Autowired
    private WebSocketServerConfig imServerConfig;
    @Autowired
    private ServerRegister serverRegister;
    @Autowired
    private DistributedIdGenerator idGenerator;

    @Autowired
    private AuthenticationHandler authenticationHandler;
    @Autowired
    private ChatFunctionHandlerSelector chatFunctionHandlerSelector;
    /**
     * 应用名称
     */
    @Value("${spring.application.name}")
    private String applicationName;


    @PostConstruct
    public void start() throws Exception {
        // 监听线程组
        bossGroup = new NioEventLoopGroup(1);
        // 工作线程组
        workGroup = new NioEventLoopGroup(imServerConfig.getWorkGroupCount());
        // 引导启动线程组
        bootstrap = new ServerBootstrap();
        //3 设置监听端口
        String ip = NetUtil.getHostAddress();
        bootstrap.group(bossGroup, workGroup)
                // 绑定ip和端口
                .localAddress(ip, imServerConfig.getPort())
                // keepalive
                .option(ChannelOption.SO_KEEPALIVE, true)
                // allocator
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                // 关闭Nagle算法
                .option(ChannelOption.TCP_NODELAY, true)
                // set nio type channel
                .channel(NioServerSocketChannel.class)
                // set chain line
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                //  HTTP解码
                                .addLast(new HttpClientCodec())
                                // Http对象聚合
                                .addLast(new HttpObjectAggregator(1024 * 60))
                                // WebSocket 消息压缩
                                .addLast( WebSocketClientCompressionHandler.INSTANCE)
                                // WebSocket 协议处理
                                .addLast(new WebSocketServerProtocolHandler(imServerConfig.getWsPath()))
                                //  空闲状态程序处理
                                .addLast(new IdleStateHandler(60, 0, 0))
                                // 认证
                                .addLast(authenticationHandler)
                                // 其它对话函数处理器
                                .addLast(chatFunctionHandlerSelector);
                    }
                });
        // 获取异步绑定端口的 CompletableFuture
        CompletableFuture<Integer> bindPortFuture = new CompletableFuture<>();
        // 执行端口绑定
        bind(bootstrap, ip, imServerConfig.getPort(), bindPortFuture);
        // 获取绑定端口
        Integer bindPort = bindPortFuture.get();
        // 服务注册
        this.registerServer(ip, bindPort, applicationName);
        // print banner
        printBanner();
    }

    /**
     * 注册服务
     *
     * @param ip              ip地址
     * @param bindPort        绑定端口号
     * @param applicationName 服务名称
     */
    private void registerServer(String ip, Integer bindPort, String applicationName) {
        long serverId = idGenerator.generateId();
        ServerInfo server = ServerInfo.builder()
                .port(bindPort)
                .ip(ip)
                .serverId(serverId)
                .connections(new AtomicInteger(0))
                .name(applicationName)
                .build();
        serverRegister.register(server);
        ServerInstanceInfo instanceInfo = ServerInstanceInfo.initInstance(serverId, server);
        log.info("server:{} is already register", JSON.toJSON(instanceInfo));
    }

    /**
     * 打印banner
     */
    private void printBanner() {
        if (!imServerConfig.isPrintBanner()) {
            return;
        }
        String banner = ".-.   .---..-.-.-..----..-..-.\n" +
                "| |__ | |- | | | || || || .` |\n" +
                "`----'`---'`-'-'-'`----'`-'`-'\n" +
                "--- lemon chat started-- version:1.0";
        System.out.println(banner);
    }

    /**
     * 自动绑定递增端口
     *
     * @param bootstrap 启动线程组
     * @param ipHost    IP地址
     * @param port      初始绑定端口
     */
    private void bind(final ServerBootstrap bootstrap, final String ipHost, final Integer port, CompletableFuture<Integer> bindPortFuture) throws InterruptedException {
        bootstrap.bind(new InetSocketAddress(ipHost, port)).addListener(future -> {
            if (!future.isSuccess()) {
                log.warn("Chat server started failed on port {}", port);
                Integer newPort = port + 1;
                bind(bootstrap, ipHost, newPort, bindPortFuture);
            } else {
                bindPortFuture.complete(port);
                log.info("Chat server started on port {}", port);
            }
        });
    }

    /**
     * 应用停止时执行
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        String applicationName = event.getApplicationContext().getApplicationName();
        Long serverId = ServerInstanceInfo.instance.getServerId();
        serverRegister.offline(serverId);
        log.info("{} server is offline", applicationName);
    }
}