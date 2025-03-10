package com.owl.jdbc.model;

import java.util.List;
import java.util.Map;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2022/12/29.
 */
public class InsertListInfo {
    private String dbType;
    private String tableName;
    private List<Map<String, String>> params;

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Map<String, String>> getParams() {
        return params;
    }

    public void setParams(List<Map<String, String>> params) {
        this.params = params;
    }
}
