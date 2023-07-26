package com.tcloud.zkclient.common.exceptions;

public class ZkNodeNoDataException extends RuntimeException{

    public ZkNodeNoDataException() {
        super();
    }

    public ZkNodeNoDataException(String message) {
        super(message);
    }
}
