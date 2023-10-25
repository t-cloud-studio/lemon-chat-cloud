package com.tcloud.im.router.cache;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 网关连接会话缓存
 *
 * @author Anker
 */
public class GatewayChannelCacheHolder {


    /**
     * WebSocket channel 缓存
     *
     * @k serverId
     * @v channel会话
     */
    private static final Map<String, Channel> WS_GATE_CHANNEL = new ConcurrentHashMap<>(8);


    public static boolean exists(String serverId){
        return WS_GATE_CHANNEL.containsKey(serverId);
    }


    public static Channel gtChannel(String serverId){
        return WS_GATE_CHANNEL.get(serverId);
    }


    public static void setGtChannel(String serverId, Channel gtChannel){
        if (exists(serverId)){
            return;
        }
        WS_GATE_CHANNEL.put(serverId,gtChannel);
    }



}
