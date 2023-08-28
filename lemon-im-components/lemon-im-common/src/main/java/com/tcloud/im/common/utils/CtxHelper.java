package com.tcloud.im.common.utils;

import com.alibaba.fastjson2.JSON;
import com.tcloud.im.common.domain.WsResult;
import com.tcloud.im.common.enums.WsRespCode;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketCloseStatus;
import io.netty.util.AttributeKey;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Objects;

import static com.tcloud.im.common.constants.ChannelAttrKeys.PATH_PARAMETERS_KEY;

/**
 * Ctx 辅助器
 *
 * @author Anlker
 */
@UtilityClass
public class CtxHelper {


    /**
     * 写文本
     *
     * @param ctx  channel
     * @param data  数据
     */
    public void writeSuccess(ChannelHandlerContext ctx, Object data){
        WsResult<Object> result = WsResult.data(data);
        writeJson(ctx, result);
    }

    /**
     * 写文本
     *
     * @param ctx  channel
     * @param msg  消息
     */
    public void writeSuccess(ChannelHandlerContext ctx, String msg){
        WsResult<Object> result = WsResult.data(null, msg);
        writeJson(ctx, result);
    }

    /**
     * 失败
     *
     * @param ctx  channel
     * @param msg  提示消息
     */
    public void writeFail(ChannelHandlerContext ctx, String msg){
        WsResult<Object> result = WsResult.fail(msg);
        writeJson(ctx, result);
    }



    /**
     * 失败并关闭连接
     *
     * @param ctx  channel
     * @param msg  提示消息
     */
    public void writeFailAndClose(ChannelHandlerContext ctx, WsRespCode respCode, String msg){
        WsResult<Object> result = WsResult.fail(respCode, msg);
        writeJson(ctx, result);
        close(ctx, msg);
    }


    /**
     * 失败并关闭连接
     *
     * @param ctx  channel
     * @param msg  提示消息
     */
    public void writeFailAndClose(ChannelHandlerContext ctx, String msg){
        WsResult<Object> result = WsResult.fail(msg);
        writeJson(ctx, result);
        close(ctx, msg);
    }


    /**
     * 写文本
     *
     * @param ctx channel
     * @param content 内容
     */
    public void writeText(ChannelHandlerContext ctx, String content){
        TextWebSocketFrame responseFrame = new TextWebSocketFrame(content);
        ctx.channel().writeAndFlush(responseFrame);
    }

    /**
     * 写JSON
     *
     * @param ctx       channel
     * @param content   内容
     */
    public void writeJson(ChannelHandlerContext ctx, Object content){
        String jsonString = JsonUtil.toJson(content);
        writeText(ctx, jsonString);
    }


    public void close(ChannelHandlerContext ctx) {
        CloseWebSocketFrame closeWebSocketFrame = new CloseWebSocketFrame(WebSocketCloseStatus.NORMAL_CLOSURE);
        ctx.channel().writeAndFlush(closeWebSocketFrame);
        // 关闭通道
        ctx.close();
    }

    public void close(ChannelHandlerContext ctx, String reason) {
        // 发送关闭帧
        CloseWebSocketFrame closeWebSocketFrame = new CloseWebSocketFrame(WebSocketCloseStatus.NORMAL_CLOSURE, reason);
        ctx.channel().writeAndFlush(closeWebSocketFrame);
        // 关闭通道
        ctx.close();
    }

    /**
     * 设置Attr
     *
     * @param ctx       channel
     * @param attrKey   key
     * @param data      var
     * @param <T>       泛型
     */
    public static <T> void setAttr(ChannelHandlerContext ctx, AttributeKey<T> attrKey, T data) {
        ctx.channel().attr(attrKey).set(data);
    }


    /**
     * 设置Attr
     *
     * @param ctx       channel
     * @param attrKey   key
     * @param data      var
     * @param <T>       泛型
     */
    public static <T> T getAttr(ChannelHandlerContext ctx, AttributeKey<T> attrKey) {
        return ctx.channel().attr(attrKey).get();
    }



    /**
     * 设置Attr
     *
     * @param ctx       channel
     * @param attrKey   key
     * @param data      var
     * @param <T>       泛型
     */
    public static String getStrPathParam(ChannelHandlerContext ctx,String k) {
        Map<String, String> attr = getAttr(ctx, PATH_PARAMETERS_KEY);
        if (Objects.isNull(attr)){
            return null;
        }
        return attr.get(k);
    }
}
