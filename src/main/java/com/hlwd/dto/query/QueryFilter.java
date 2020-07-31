package com.hlwd.dto.query;

public class QueryFilter {
    private String field = "";
    private String operator = "";
    private String value = "";

    public QueryFilter() {
    }

    public QueryFilter(String field, String value) {
        this.field = field;
        this.operator = "=";
        this.value = value;
    }

    public QueryFilter(String field, String operator, String value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }

    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public boolean equals(Object obj) {
        QueryFilter other = (QueryFilter)obj;
        return this.field.equals(other.getField());
    }
}