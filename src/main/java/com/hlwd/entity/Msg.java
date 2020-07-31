package com.hlwd.entity;

import com.hlwd.dto.ajax.BaseResult;

import java.util.Date;

public class Msg extends BaseResult {
    private String msg;
    private String content;
    private Date time;

    public Msg() {
        this.msg = "-";
        this.content = "";
        this.time = new Date(1375864674543L);
    }

    public Msg(int code) {
        this.msg = "-";
        this.content = "";
        this.time = new Date(1375864674543L);
        super.setCode(code);
    }

    public Msg(int code, String msg) {
        this.msg = "-";
        this.content = "";
        this.time = new Date(1375864674543L);
        super.setCode(code);
        this.msg = msg;
    }

    public Msg(int code, String msg, String content) {
        this(code, msg);
        this.content = content;
    }

    public Msg(int code, String msg, String content, Date time) {
        this(code, msg, content);
        this.time = time;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}