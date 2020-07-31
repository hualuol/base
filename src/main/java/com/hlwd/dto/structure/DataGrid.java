package com.hlwd.dto.structure;

import java.util.ArrayList;
import java.util.List;

public class DataGrid<T> {
    private int total = 0;
    private List<T> rows = new ArrayList();

    public DataGrid() {
    }

    public DataGrid(List<T> rows, int total) {
        this.rows = rows;
        this.total = total;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return this.rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}