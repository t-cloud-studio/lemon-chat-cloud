package com.tcloud.session.server;

import com.tcloud.imcommon.utils.NetUtil;
import com.tcloud.protocol.codec.ProtobufDecoder;
import com.tcloud.protocol.codec.ProtobufEncoder;
import com.tcloud.session.config.ChatServerConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

/**
 * IM Netty 服务
 *
 * @author Anker
 */
@Slf4j
@Configuration
@ConditionalOnBean(ChatServerConfig.class)
public class ChatNettyServer {

    private EventLoopGroup bossGroup;

    private EventLoopGroup workGroup;

    private ServerBootstrap bootstrap;

    @Autowired
    private ChatServerConfig chatServerConfig;


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
        // 执行端口绑定
        bind(bootstrap, ip, chatServerConfig.getPort());
        // print banner
        printBanner();
    }

    private void printBanner() {
        if (!chatServerConfig.isPrintBanner()){
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
    private void bind(final ServerBootstrap bootstrap, final String ipHost, final Integer port) throws InterruptedException {
        bootstrap.bind(new InetSocketAddress(ipHost, port)).addListener(future -> {
            if (!future.isSuccess()) {
                log.warn("Chat server started failed on port {}", port);
                bind(bootstrap, ipHost, port + 1);
            }
            log.info("Chat server started on port {}", port);
        }).sync();
    }


}
