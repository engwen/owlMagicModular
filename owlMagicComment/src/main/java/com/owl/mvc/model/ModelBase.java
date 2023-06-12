package com.owl.mvc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.owl.model.ModelPrototype;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 设置ID
 * author engwen
 * email xiachanzou@outlook.com
 * 2021/6/5.
 */
public abstract class ModelBase<ID> extends ModelPrototype {
    protected ID id;

    //数据创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected String createTime;

    //最后更新时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected String updateTime;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

}
