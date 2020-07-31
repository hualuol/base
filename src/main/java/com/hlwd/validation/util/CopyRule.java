package com.hlwd.validation.util;

public class CopyRule {
    private String fieldName = "";
    private int type = 0;
    private String template = "";

    public CopyRule(String fieldName) {
        this.fieldName = fieldName;
    }

    public CopyRule(String fieldName, String template) {
        this.fieldName = fieldName;
        this.template = template;
    }

    public CopyRule(String fieldName, String template, int type) {
        this.fieldName = fieldName;
        this.template = template;
        this.type = type;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTemplate() {
        return this.template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}