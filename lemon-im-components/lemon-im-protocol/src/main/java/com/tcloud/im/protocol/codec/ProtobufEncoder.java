package com.tcloud.im.protocol.codec;


import com.tcloud.im.common.constants.ProtocolConstant;
import com.tcloud.im.protocol.msg.LemonMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 编码器
 *
 * @author Anker
 */

@Slf4j
public class ProtobufEncoder extends MessageToByteEncoder<LemonMessage> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, LemonMessage lemonMessage, ByteBuf byteBuf) throws Exception {
        byteBuf.writeByte(ProtocolConstant.MAGIC_NUMBER);
        byteBuf.writeInt(ProtocolConstant.VERSION);
        byteBuf.writeByte(lemonMessage.getCmd());
        byteBuf.writeByte(lemonMessage.getMsgType());
        byteBuf.writeInt(lemonMessage.getLogId());
        byteBuf.writeInt(lemonMessage.getSequenceId());
        byteBuf.writeInt(lemonMessage.getData().length);
        byteBuf.writeBytes(lemonMessage.getData());
    }

}
