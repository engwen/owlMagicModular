package com.owl.mvc.so;

import com.owl.mvc.dto.ModelDTO;
import com.owl.mvc.dto.PageDTO;

import java.util.Date;

/**
 * 本類使用于查詢 dao 接口，爲了方便并行 sql ，因此使用本封裝
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/1/3.
 */
public class SelectLikeSO<T> {
    private T model;
    private Integer upLimit;
    private Integer rows;
    private Date startTime;
    private Date endTime;

    public static <T> SelectLikeSO<T> getInstance(T model) {
        return new SelectLikeSO<>(model);
    }

    public static <T> SelectLikeSO<T> getInstance(ModelDTO<T> modelDTO) {
        SelectLikeSO<T> selectLikeSO = new SelectLikeSO<>(modelDTO.getModel());
        selectLikeSO.setSETime(modelDTO.getStartTime(), modelDTO.getEndTime());
        return selectLikeSO;
    }

    public static <T> SelectLikeSO<T> getInstance(PageDTO<T> pageDTO) {
        SelectLikeSO<T> selectLikeSO = new SelectLikeSO<>(pageDTO.getModel());
        selectLikeSO.setSETime(pageDTO.getStartTime(), pageDTO.getEndTime());
        return selectLikeSO;
    }

    public static <T> SelectLikeSO<T> getInstance(T model, Date startTime, Date endTime) {
        SelectLikeSO<T> selectLikeSO = new SelectLikeSO<>(model);
        selectLikeSO.setSETime(startTime, endTime);
        return selectLikeSO;
    }

    public static <T> SelectLikeSO<T> getInstance(T model, Integer upLimit, Integer rows) {
        return new SelectLikeSO<>(model, upLimit, rows);
    }

    private SelectLikeSO(T model) {
        this.model = model;
    }

    private SelectLikeSO(T model, Integer upLimit, Integer rows) {
        this.model = model;
        this.upLimit = upLimit;
        this.rows = rows;
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

    public Integer getUpLimit() {
        return upLimit;
    }

    public void setUpLimit(Integer upLimit) {
        this.upLimit = upLimit;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
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
