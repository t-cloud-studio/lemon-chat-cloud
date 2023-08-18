package com.tcloud.im.lsb.exceptions;

public class NotAvailableServerException extends RuntimeException{

    public NotAvailableServerException() {
        super();
    }

    public NotAvailableServerException(String message) {
        super(message);
    }

    public NotAvailableServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAvailableServerException(Throwable cause) {
        super(cause);
    }

    protected NotAvailableServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
