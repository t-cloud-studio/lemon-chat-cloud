package com.tcloud.im.gateway.websocket.cache;

import com.google.common.collect.Maps;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.Objects;

/**
 * socket 会话缓存
 *
 * @author evans
 * @description
 * @date 2023/8/23
 */
public class SocketSessionCache {



    /**
     * 当前服务器会话列表
     *
     * @k userId
     * @v session
     */
    private static Map<Long, ChannelHandlerContext> SESSION_MAP = Maps.newConcurrentMap();


    /**
     * 添加会话
     *
     * @param userId  用户id
     * @param channel 会话信息
     */
    public static void addSession(Long userId, ChannelHandlerContext channel) {
        if (Objects.isNull(channel)) {
            throw new NullPointerException("the client session must not be null!!!");
        }
        SESSION_MAP.put(userId, channel);
    }

    /**
     * 移除会话
     *
     * @param userId 用户id
     */
    public static void removeSession(Long userId) {
        if (!SESSION_MAP.containsKey(userId)) {
            return;
        }
        SESSION_MAP.remove(userId);
    }

    /**
     * 移除会话
     *
     * @param userId 用户id
     */
    public static boolean exists(Long userId) {
        return SESSION_MAP.containsKey(userId);
    }

    public static ChannelHandlerContext get(Long userId){
        return SESSION_MAP.get(userId);
    }

}
