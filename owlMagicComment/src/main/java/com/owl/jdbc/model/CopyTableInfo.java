package com.owl.jdbc.model;

import java.util.Map;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2022/12/29.
 */
public class CopyTableInfo {
    private String sourceTableName;//源表
    private String toTableName;//目标表
    private Map<String, String> keyMapping;//键键映射关系，key为源表中的列名，value为目标表中的列名

    private String sourceType;
    private String toType;
    private String orderBy;

    public String getSourceTableName() {
        return sourceTableName;
    }

    public void setSourceTableName(String sourceTableName) {
        this.sourceTableName = sourceTableName;
    }

    public String getToTableName() {
        return toTableName;
    }

    public void setToTableName(String toTableName) {
        this.toTableName = toTableName;
    }

    public Map<String, String> getKeyMapping() {
        return keyMapping;
    }

    public void setKeyMapping(Map<String, String> keyMapping) {
        this.keyMapping = keyMapping;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getToType() {
        return toType;
    }

    public void setToType(String toType) {
        this.toType = toType;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
