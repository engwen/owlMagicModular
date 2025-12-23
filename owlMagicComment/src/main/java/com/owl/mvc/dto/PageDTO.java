package com.owl.mvc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Map;

/**
 * 界面接收类
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/2/26.
 */
public class PageDTO<T> {
    /*
     *  @param getAll      获取所有
     *  @param requestPage 请求页数
     *  @param rows        请求显示条数
     *  @param model       检索对象属性
     */
    private boolean getAll;
    private Integer requestPage;
    private Integer rows;
    private T model;
    private boolean export = false;
    private Map<String,String> keyValues;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    public PageDTO() {
    }

    private PageDTO(boolean getAll, Integer requestPage, Integer rows, T model) {
        this.getAll = getAll;
        this.requestPage = requestPage;
        this.rows = rows;
        this.model = model;
    }

    public static <T> PageDTO<T> getInstance(boolean getAll, Integer requestPage, Integer rows, T model) {
        return new PageDTO<>(getAll, requestPage, rows, model);
    }

    public boolean getGetAll() {
        return getAll;
    }

    public void setGetAll(boolean getAll) {
        this.getAll = getAll;
    }

    public Integer getRequestPage() {
        return requestPage;
    }

    public void setRequestPage(Integer requestPage) {
        this.requestPage = requestPage;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean isGetAll() {
        return getAll;
    }

    public boolean isExport() {
        return export;
    }

    public void setExport(boolean export) {
        this.export = export;
    }

    public Map<String, String> getKeyValues() {
        return keyValues;
    }

    public void setKeyValues(Map<String, String> keyValues) {
        this.keyValues = keyValues;
    }
}
