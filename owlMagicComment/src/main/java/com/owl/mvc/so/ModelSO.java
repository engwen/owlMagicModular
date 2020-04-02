package com.owl.mvc.so;

import java.util.Date;

/**
 * 标准对象
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/4/12.
 */
public class ModelSO<T> {
    private T model;
    private Date startTime;
    private Date endTime;

    private ModelSO(T model) {
        this.model = model;
    }

    public static <T> ModelSO<T> getInstance(T model) {
        return new ModelSO<>(model);
    }

    public void setSETime(Date startTime, Date endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
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
