package com.owl.mvc.so;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 标准对象
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/4/12.
 */
public class ModelSO<T> {
    private T model;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    private ModelSO(T model) {
        this.model = model;
    }

    public static <T> ModelSO<T> getInstance(T model) {
        return new ModelSO<>(model);
    }

    public static <T> ModelSO getInstance(T model, Date startTime, Date endTime) {
        ModelSO<T> tModelSO = new ModelSO<>(model);
        tModelSO.setSETime(startTime, endTime);
        return tModelSO;
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
