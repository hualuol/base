package com.hlwd.dto.ajax;

public class BaseResult {
    private int code=0;
    private String msg = "";

    public BaseResult() {
    }


    public BaseResult(int code) {
        this.code = code;
    }
    public BaseResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
