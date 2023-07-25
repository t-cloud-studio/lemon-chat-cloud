package com.tcloud.protocol.codec;


import com.tcloud.imcommon.constants.ProtocolConstant;
import com.tcloud.protocol.message.ProtoMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * create by 尼恩 @ 疯狂创客圈
 * <p>
 * 编码器
 */

@Slf4j
public class ProtobufEncoder extends MessageToByteEncoder<ProtoMessage.Message>
{

    @Override
    protected void encode(ChannelHandlerContext ctx,
                          ProtoMessage.Message msg, ByteBuf out)
            throws Exception
    {
        out.writeShort(ProtocolConstant.MAGIC_NUMBER);
        out.writeShort(ProtocolConstant.VERSION);

        byte[] bytes = msg.toByteArray();// 将对象转换为byte

        // 加密消息体
        /*ThreeDES des = channel.channel().attr(Constants.ENCRYPT).get();
        byte[] encryptByte = des.encrypt(bytes);*/
        int length = bytes.length;// 读取消息的长度


        // 先将消息长度写入，也就是消息头
        out.writeInt(length);
        // 消息体中包含我们要发送的数据
        out.writeBytes(msg.toByteArray());

/*        log.debug("send "
                + "[remote ip:" + ctx.channel().remoteAddress()
                + "][total length:" + length
                + "][bare length:" + msg.getSerializedSize() + "]");*/


    }

}
