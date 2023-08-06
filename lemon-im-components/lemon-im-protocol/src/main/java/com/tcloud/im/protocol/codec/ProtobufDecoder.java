package com.tcloud.im.protocol.codec;

import com.tcloud.im.common.constants.ProtocolConstant;
import com.tcloud.im.common.exceptions.InvalidFrameException;
import com.tcloud.im.protocol.msg.LemonMessage;
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
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        // 标记一下当前的readIndex的位置
        byteBuf.markReaderIndex();
        // 判断包头长度
        if (byteBuf.readableBytes() < ProtocolConstant.BASE_LENGTH) {
            return;
        }
        //读取魔数
        short magic = byteBuf.readShort();
        if (magic != ProtocolConstant.MAGIC_NUMBER) {
            log.warn(ctx.channel().remoteAddress() + " 解码失败,Magic Number is wrong!");
            throw new InvalidFrameException("非法访问:" + ctx.channel().remoteAddress());
        }
        // 跳过不需要读取的字节码
        if (byteBuf.readableBytes() > ProtocolConstant.MAX_DATA_LENGTH) {
            byteBuf.skipBytes(byteBuf.readableBytes());
        }
        int beginReader;
        while (true) {
            // 获取包头开始的index
            beginReader = byteBuf.readerIndex();
            // 标记包头开始的index
            byteBuf.markReaderIndex();
            if (byteBuf.readByte() == ProtocolConstant.MAGIC_NUMBER) {
                break;
            }
            // 到这里表明没有读取到包头标识，继续往后读取一个字节
            byteBuf.resetReaderIndex();
            byteBuf.readByte();
            // 等待完整数据包
            if (byteBuf.readableBytes() < ProtocolConstant.BASE_LENGTH) {
                return;
            }
        }
        // 到这表明读取到了头标识magic，需要读取头其他数据
        int version = byteBuf.readInt();
        byte cmd = byteBuf.readByte();
        byte msgType = byteBuf.readByte();
        int logId = byteBuf.readInt();
        int sequenceId = byteBuf.readInt();
        int bodyLength = byteBuf.readInt();
        if (byteBuf.readableBytes() < bodyLength) {
            // 表明 body还未读取完，还原读指针
            byteBuf.readerIndex(beginReader);
            return;
        }
        byte[] bodyData = new byte[bodyLength];
        byteBuf.readBytes(bodyData);

        LemonMessage protocol = new LemonMessage();
        protocol.setVersion(version);
        protocol.setCmd(cmd);
        protocol.setMsgType(msgType);
        protocol.setLogId(logId);
        protocol.setSequenceId(sequenceId);
        protocol.setData(bodyData);
        out.add(protocol);
    }
}
