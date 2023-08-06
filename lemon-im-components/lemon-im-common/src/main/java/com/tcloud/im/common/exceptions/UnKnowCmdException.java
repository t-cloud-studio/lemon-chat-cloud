package com.tcloud.im.common.exceptions;

/**
 * @author evans
 * @description
 * @date 2023/8/6
 */
public class UnKnowCmdException extends RuntimeException{


    public UnKnowCmdException() {
        super();
    }

    public UnKnowCmdException(String message) {
        super(message);
    }

    public UnKnowCmdException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnKnowCmdException(Throwable cause) {
        super(cause);
    }

    protected UnKnowCmdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
