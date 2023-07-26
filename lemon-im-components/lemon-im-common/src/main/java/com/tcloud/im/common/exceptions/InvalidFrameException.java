package com.tcloud.im.common.exceptions;

public class InvalidFrameException extends RuntimeException{

    public InvalidFrameException() {
        super();
    }

    public InvalidFrameException(String message) {
        super(message);
    }

    public InvalidFrameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidFrameException(Throwable cause) {
        super(cause);
    }

    protected InvalidFrameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
