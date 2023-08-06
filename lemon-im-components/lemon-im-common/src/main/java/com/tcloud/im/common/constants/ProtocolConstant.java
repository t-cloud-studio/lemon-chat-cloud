package com.tcloud.im.common.constants;

/**
 * 协议常量
 *
 * @author Anker
 */
public interface ProtocolConstant {


    /**
     * 魔数
     */
    short MAGIC_NUMBER = 0x4C;

    /**
     * 版本 1 ipv4， 2ipv6
     */
    short VERSION = 0x01;

    /**
     * 基本长度
     */
    int BASE_LENGTH = 8;

    /**
     * 最大数据长度
     */
    int MAX_DATA_LENGTH = 2048;

}
