package com.tcloud.web.common.exceptions;

import com.tcloud.web.common.enums.ResultCode;

public class ApplicationBizException extends RuntimeException{

    public final ResultCode CODE = ResultCode.FAILURE;

    public ApplicationBizException() {
        super();
    }

    public ApplicationBizException(String message) {
        super(message);
    }

    public ApplicationBizException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationBizException(Throwable cause) {
        super(cause);
    }

    protected ApplicationBizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static void throwException(String message) {
        throw new ApplicationBizException(message);
    }
}
