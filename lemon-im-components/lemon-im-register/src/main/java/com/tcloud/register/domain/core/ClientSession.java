package com.tcloud.register.domain.core;

import com.tcloud.register.domain.pojo.UserInfo;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Data;

@Data
public class ClientSession {


    private String sessionId;


    private UserInfo userInfo;


    private Server server;


    private NioSocketChannel channel;


}
