package com.rttmall.shopbackend.exception;

/**
 * Created by wangweibin on 2017/2/22.
 */
public class ServiceException extends RuntimeException {
    private String message;

    public ServiceException(String message) {
        super(message);
        this.message = message;
    }
}
