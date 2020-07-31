package com.hlwd.dto.ajax;

public class JsonResult extends ExternalResult {

    public JsonResult(int code) {
        super(code);
    }

    public JsonResult(int code, String msg) {
        super(code, msg);
    }

    public JsonResult(int code, String msg, Object data) {
        super(code, msg, data);
    }
}
