package com.hlwd.exception;

public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1464240615837204413L;
    private String clientMsg;

    public BaseException() {
        this.clientMsg = "";
    }

    public BaseException(String errMsg) {
        super(errMsg);
        this.clientMsg = "";
    }

    public BaseException(String errMsg, String clientMsg) {
        this(errMsg);
        this.clientMsg = clientMsg;
    }

    public BaseException(String errMsg, Throwable cause, String clientMsg) {
        super(errMsg, cause);
        this.clientMsg = "";
        this.clientMsg = clientMsg;
    }

    public String getClientMsg() {
        return this.clientMsg;
    }
}