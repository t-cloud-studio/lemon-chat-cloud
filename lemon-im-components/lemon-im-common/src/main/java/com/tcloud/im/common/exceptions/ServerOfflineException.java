package com.tcloud.im.common.exceptions;

public class ServerOfflineException extends RuntimeException{

    public ServerOfflineException() {
        super();
    }

    public ServerOfflineException(String message) {
        super(message);
    }

    public ServerOfflineException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerOfflineException(Throwable cause) {
        super(cause);
    }

    protected ServerOfflineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
