package com.tcloud.im.common.domain;

import com.tcloud.im.common.enums.WsRespCode;
import lombok.Data;

/**
 * @author evans
 * @description
 * @date 2023/8/26
 */
@Data
public class WsResult<T> {

    /**
     * 响应码
     */
    private Integer code;
    /**
     * 响应消息
     */
    private String msg;
    /**
     * 结果消息
     */
    private T data;



    private WsResult(WsRespCode WsRespCode) {
        this(WsRespCode, null, WsRespCode.getMsg());
    }

    private WsResult(WsRespCode WsRespCode, String msg) {
        this(WsRespCode, null, msg);
    }

    private WsResult(WsRespCode WsRespCode, T data) {
        this(WsRespCode, data, WsRespCode.getMsg());
    }

    private WsResult(WsRespCode WsRespCode, T data, String msg) {
        this(WsRespCode.getCode(), data, msg);
    }

    private WsResult(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    /**
     * 返回 R
     *
     * @param data 数据
     * @param <T>  T 泛型标记
     * @return R
     */
    public static <T> WsResult<T> data(T data) {
        return data(data, WsRespCode.SUCCESS.getMsg());
    }

    /**
     * 返回 R
     *
     * @param data 数据
     * @param msg  消息
     * @param <T>  T 泛型标记
     * @return R
     */
    public static <T> WsResult<T> data(T data, String msg) {
        return data(WsRespCode.SUCCESS.getCode(), data, msg);
    }

    /**
     * 返回 R
     *
     * @param code 状态码
     * @param data 数据
     * @param msg  消息
     * @param <T>  T 泛型标记
     * @return R
     */
    public static <T> WsResult<T> data(int code, T data, String msg) {
        return new WsResult<>(code, data, msg);
    }

    /**
     * 返回 R
     *
     * @param msg 消息
     * @param <T> T 泛型标记
     * @return R
     */
    public static <T> WsResult<T> fail(String msg) {
        return data(WsRespCode.BIZ_ERROR.getCode(), null, msg);
    }

    /**
     * 返回 R
     *
     * @param respCode 状态码
     * @param msg        消息
     * @param <T>        T 泛型标记
     * @return R
     */
    public static <T> WsResult<T> fail(WsRespCode respCode, String msg) {
        return new WsResult<>(respCode, msg);
    }

}
