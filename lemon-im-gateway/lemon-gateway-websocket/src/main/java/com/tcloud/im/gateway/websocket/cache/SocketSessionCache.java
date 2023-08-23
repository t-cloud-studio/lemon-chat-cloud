package com.tcloud.im.gateway.websocket.cache;

import com.google.common.collect.Maps;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Map;
import java.util.Objects;

/**
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
    private Map<Long, NioSocketChannel> SESSION_MAP = Maps.newConcurrentMap();


    /**
     * 添加会话
     *
     * @param userId  用户id
     * @param channel 会话信息
     */
    public void addSession(Long userId, NioSocketChannel channel) {
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
    public void removeSession(Long userId) {
        if (!SESSION_MAP.containsKey(userId)) {
            return;
        }
        SESSION_MAP.remove(userId);
    }


}
