package com.owl.mvc.dto;

import java.util.List;

/**
 * 关系接收类
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/4/29.
 */
public class RelationDTO<T> {
    private T model;
    private List<T> modelList;


    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    public List<T> getModelList() {
        return modelList;
    }

    public void setModelList(List<T> modelList) {
        this.modelList = modelList;
    }
}
