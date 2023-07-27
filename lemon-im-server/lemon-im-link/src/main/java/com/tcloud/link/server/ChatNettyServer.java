package com.tcloud.link.server;

import com.tcloud.im.common.utils.NetUtil;
import com.tcloud.im.protocol.codec.ProtobufDecoder;
import com.tcloud.im.protocol.codec.ProtobufEncoder;
import com.tcloud.link.config.ChatServerConfig;
import com.tcloud.register.ServerRegister;
import com.tcloud.register.domain.core.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * IM Netty 服务
 *
 * @author Anker
 */
@Slf4j
@Configuration
@ConditionalOnBean(ChatServerConfig.class)
public class ChatNettyServer implements ApplicationListener<ContextClosedEvent> {

    private EventLoopGroup bossGroup;

    private EventLoopGroup workGroup;

    private ServerBootstrap bootstrap;

    @Autowired
    private ChatServerConfig chatServerConfig;
    @Autowired
    private ServerRegister serverRegister;


    @PostConstruct
    public void start() throws Exception {
        // 监听线程组
        bossGroup = new NioEventLoopGroup(1);
        // 工作线程组
        workGroup = new NioEventLoopGroup(chatServerConfig.getWorkGroupCount());
        // 引导启动线程组
        bootstrap = new ServerBootstrap();
        //3 设置监听端口
        String ip = NetUtil.getHostAddress();
        bootstrap.group(bossGroup, workGroup)
                // 绑定ip和端口
                .localAddress(ip, chatServerConfig.getPort())
                // keepalive
                .option(ChannelOption.SO_KEEPALIVE, true)
                // allocator
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                // 关闭Nagle算法
                .option(ChannelOption.TCP_NODELAY, true)
                // set nio type channel
                .channel(NioServerSocketChannel.class)
                // set chain line
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new ProtobufDecoder())
                                .addLast(new ProtobufEncoder()).addLast().addLast().addLast();
                    }
                });
        // 获取异步绑定端口的 CompletableFuture
        CompletableFuture<Integer> bindPortFuture = new CompletableFuture<>();
        // 执行端口绑定
        bind(bootstrap, ip, chatServerConfig.getPort(), bindPortFuture);
        // 服务注册
        this.registerServer(ip, bindPortFuture, chatServerConfig.getServerName());
        // print banner
        printBanner();
    }

    private void registerServer(String ip, CompletableFuture<Integer> bindPortFuture, String serverName) throws ExecutionException, InterruptedException {
        Integer finalBindPort = bindPortFuture.get();
        Server server = Server.builder()
                .port(finalBindPort)
                .ip(ip)
                .connections(new AtomicInteger(0))
                .name(serverName)
                .build();
        serverRegister.registerServer(server);
    }

    private void printBanner() {
        if (!chatServerConfig.isPrintBanner()) {
            return;
        }
        String banner = ".-.   .---..-.-.-..----..-..-.\n" +
                "| |__ | |- | | | || || || .` |\n" +
                "`----'`---'`-'-'-'`----'`-'`-'\n" +
                "------ lemon chat-- version:1.0";
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
                bind(bootstrap, ipHost, port + 1, bindPortFuture);
            }
            bindPortFuture.complete(port);
            log.info("Chat server started on port {}", port);
        }).sync();
    }

    /**
     * 应用停止时执行
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        serverRegister.offlineServer(chatServerConfig.getServerName());
    }
}