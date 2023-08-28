package com.tcloud.im.router.cache;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 网关连接会话缓存
 *
 * @author Anker
 */
public class GatewayChannelCache {



    public static final Map<String, Channel> GATE_CHANNEL = new ConcurrentHashMap<>(8);




}
