package com.owl.mvc.dto;

import java.util.Date;

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

    private Date startTime;
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
}
