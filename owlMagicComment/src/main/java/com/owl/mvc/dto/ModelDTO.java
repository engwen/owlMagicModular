package com.owl.mvc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2020/4/2.
 */
public class ModelDTO<T> {
    private T model;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    public static <T> ModelDTO<T> getInstance(T model, Date startTime, Date endTime) {
        return new ModelDTO<>(model, startTime, endTime);
    }

    private ModelDTO(T model, Date startTime, Date endTime) {
        this.model = model;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ModelDTO() {
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
