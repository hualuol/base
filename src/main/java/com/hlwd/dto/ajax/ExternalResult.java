package com.hlwd.dto.ajax;

public class ExternalResult extends BaseResult {
    private Object data = null;

    public ExternalResult() {
    }

    public ExternalResult(int code) {
        super(code);
    }

    public ExternalResult(int code, String msg) {
        super(code, msg);
    }

    public ExternalResult(int code, String msg, Object data) {
        super(code, msg);
        this.data = data;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
