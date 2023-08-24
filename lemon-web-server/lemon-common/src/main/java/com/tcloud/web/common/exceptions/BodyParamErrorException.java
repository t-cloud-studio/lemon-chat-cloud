package com.tcloud.web.common.exceptions;

import com.tcloud.web.common.enums.ResultCode;

public class BodyParamErrorException extends RuntimeException{

    public final ResultCode CODE = ResultCode.PARAM_ERROR;


    public BodyParamErrorException() {
        super();
    }

    public BodyParamErrorException(String message) {
        super(message);
    }

    public BodyParamErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public BodyParamErrorException(Throwable cause) {
        super(cause);
    }

    protected BodyParamErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
