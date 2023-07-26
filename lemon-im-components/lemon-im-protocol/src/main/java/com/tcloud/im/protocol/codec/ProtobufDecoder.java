package com.tcloud.im.protocol.codec;

import com.tcloud.im.common.constants.ProtocolConstant;
import com.tcloud.im.common.exceptions.InvalidFrameException;
import com.tcloud.im.protocol.message.ProtoMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 解码器
 *
 * @author Anker
 */
@Slf4j
public class ProtobufDecoder extends ByteToMessageDecoder {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 标记一下当前的readIndex的位置
        in.markReaderIndex();
        // 判断包头长度
        if (in.readableBytes() < 8) {// 不够包头
            return;
        }
        //读取魔数
        short magic = in.readShort();
        if (magic != ProtocolConstant.MAGIC_NUMBER) {
            log.warn(ctx.channel().remoteAddress() + " 解码失败,Magic Number is wrong!");
            throw new InvalidFrameException("非法访问:" + ctx.channel().remoteAddress());
        }
        //读取版本
        short version = in.readShort();
        log.info("the msg version:{}", version);
        // 读取传送过来的消息的长度。
        int length = in.readInt();

        // 长度如果小于0 非法数据，关闭连接
        if (length < 0) {
            ctx.close();
        }
        // 读到的消息体长度如果小于传送过来的消息长度
        if (length > in.readableBytes()) {
            // 重置读取位置
            in.resetReaderIndex();
            return;
        }

        byte[] array;
        if (in.hasArray()) {
            //堆缓冲 读取 未读部分的 length 长度
            ByteBuf slice = in.slice(in.readerIndex(), length);
            array = slice.array();
            in.retain();
        } else {
            //直接缓冲
            array = new byte[length];
            in.readBytes(array, 0, length);
        }
        // 字节转成对象
        ProtoMessage.Message outmsg = ProtoMessage.Message.parseFrom(array);
        if (in.hasArray()) {
            in.release();
        }
        if (outmsg != null) {
            // 获取业务消息
            out.add(outmsg);
        }

    }
}
