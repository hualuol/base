package com.hlwd.dto.ajax;

import com.hlwd.dto.structure.DataGrid;

import java.util.ArrayList;
import java.util.List;

public class DataGridResult<T> extends JsonResult {
    private int total = 0;
    private List<T> rows = new ArrayList();

    public DataGridResult(int code) {
        super(code);
    }

    public DataGridResult(int code, String msg) {
        super(code, msg);
    }
    public DataGridResult(int code, String msg, DataGrid<T> dataGrid) {
        super(code, msg);
        this.total=dataGrid.getTotal();
        this.rows=dataGrid.getRows();
    }
    public DataGridResult(int code, String msg,int total,List rows) {
        super(code, msg);
        this.total=total;
        this.rows=rows;
    }
    public DataGridResult(int total,List rows) {
        super(200, "操作成功");
        this.total=total;
        this.rows=rows;
    }
    public int getTotal() {
        return this.total;
    }

    public List<T> getRows() {
        return this.rows;
    }
}