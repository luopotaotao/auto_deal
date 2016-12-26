package com.tt.util;

import java.util.List;

/**
 * Created by tt on 2016/12/10.
 */
public class ExcelSheet {
    private String name;
    private List<String> columns;
    private List<Object[]> data;

    public ExcelSheet(String name, List<String> columns, List<Object[]> data) {
        this.name = name;
        this.columns = columns;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public List<Object[]> getData() {
        return data;
    }

    public void setData(List<Object[]> data) {
        this.data = data;
    }
}
