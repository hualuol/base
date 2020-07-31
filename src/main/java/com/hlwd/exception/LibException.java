package com.hlwd.exception;


public class LibException extends BaseException {
    private static final long serialVersionUID = -1710398771705340973L;

    public LibException(Exception e) {
        super(e.getMessage(), "系统异常，请联系管理员");
    }

    public LibException(Exception e, String clientMsg) {
        super(e.getMessage(), clientMsg);
    }

    public LibException(String errMsg, String clientMsg) {
        super(errMsg, clientMsg);
    }
}