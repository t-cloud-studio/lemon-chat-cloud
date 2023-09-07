package com.tcloud.im.gateway.websocket.utils;

import com.alibaba.fastjson2.JSON;
import com.tcloud.common.obj.msg.WsMessage;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.experimental.UtilityClass;

/**
 * @author evans
 * @description
 * @date 2023/8/26
 */
@UtilityClass
public class WsMsgUtil {



    public <T> T covertTextJsonFrameToObject(WebSocketFrame frame, Class<T> type){
        String text = ((TextWebSocketFrame) frame).text();
        return JSON.parseObject(text, type);
    }



    public WsMessage covertTextJsonFrameToWsMsg(WebSocketFrame frame){
        return covertTextJsonFrameToObject(frame, WsMessage.class);
    }



}
