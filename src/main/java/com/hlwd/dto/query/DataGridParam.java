package com.hlwd.dto.query;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataGridParam {
    private int page = -1;
    private int rows = -1;
    private String sort = "id";
    private String order = "desc";
    private String filters = "";
    private List<QueryFilter> queryFilters = new ArrayList();
    private String privilege = null;

    public DataGridParam() {
    }

    public String getSort() {
        return this.sort;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return this.rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getFilters() {
        return this.filters;
    }

    public void setFilters(String filters) {
        try {
            this.filters = URLDecoder.decode(filters, "utf-8");
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
        }

    }

    public String getPrivilege() {
        return this.privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public List<QueryFilter> getQueryFilters() {
        return this.queryFilters;
    }

    public void setQueryFilters(List<QueryFilter> queryFilters) {
        this.queryFilters = queryFilters;
    }

    public void prepareIndex() {
        this.page = (this.page - 1) * this.rows;
    }

    public QueryFilter getFilter(String field) {
        if (this.getQueryFilters().isEmpty()) {
            return null;
        } else {
            Optional<QueryFilter> optional = this.getQueryFilters().stream().filter((f) -> {
                return field.equals(f.getField());
            }).findFirst();
            return optional.isPresent() ? (QueryFilter)optional.get() : null;
        }
    }

    public DataGridParam addFilter(QueryFilter filter) {
        List<QueryFilter> filters = new ArrayList();
        if (this.filters != null && !"".equals(this.filters)) {
            filters = JSON.parseArray(this.filters, QueryFilter.class);
        }

        ((List)filters).add(filter);
        this.filters = JSON.toJSONString(filters);
        this.queryFilters.add(filter);
        return this;
    }

    public DataGridParam removeFilter(QueryFilter filter) {
        if (("".equals(this.filters) || this.filters == null) && this.queryFilters.isEmpty()) {
            return this;
        } else {
            List<QueryFilter> filters = JSON.parseArray(this.filters, QueryFilter.class);
            filters.remove(filter);
            this.filters = JSON.toJSONString(filter);
            this.queryFilters.remove(filter);
            return this;
        }
    }

    public DataGridParam removeFilter(String field) {
        if (("".equals(this.filters) || this.filters == null) && this.queryFilters.isEmpty()) {
            return this;
        } else {
            List<QueryFilter> filters = JSON.parseArray(this.filters, QueryFilter.class);
            QueryFilter delOne = null;

            for(int i = 0; i < filters.size(); ++i) {
                QueryFilter f = (QueryFilter)filters.get(i);
                if (field.equals(f.getField())) {
                    delOne = (QueryFilter)filters.get(i);
                    break;
                }
            }

            if (delOne != null) {
                filters.remove(delOne);
                this.filters = JSON.toJSONString(filters);
                this.queryFilters.remove(delOne);
            }

            return this;
        }
    }

    public void addExtraLimits(String extraLimits) {
        if (this.privilege.contains("%s") && !"".equals(extraLimits)) {
            this.privilege = String.format(this.privilege, extraLimits);
        }

    }

    public void makeQueryFilters() {
        if (!this.getFilters().isEmpty()) {
            this.queryFilters = JSON.parseArray(this.getFilters(), QueryFilter.class);
            this.queryFilters.stream().forEach((e) -> {
                if (e.getOperator().isEmpty() || "like".equals(e.getOperator())) {
                    e.setOperator("like");
                    e.setValue("%" + e.getValue() + "%");
                }

                if ("like%".equals(e.getOperator())) {
                    e.setOperator("like");
                    e.setValue(e.getValue() + "%");
                }

                if ("%like".equals(e.getOperator())) {
                    e.setOperator("like");
                    e.setValue("%" + e.getValue());
                }

            });
        } else {
            this.queryFilters = new ArrayList();
        }

    }
}