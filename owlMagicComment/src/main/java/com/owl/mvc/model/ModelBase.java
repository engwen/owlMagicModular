package com.owl.mvc.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.owl.model.ModelPrototype;
import com.owl.util.RandomUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 设置ID
 * author engwen
 * email xiachanzou@outlook.com
 * 2021/6/5.
 */
public abstract class ModelBase<ID> extends ModelPrototype {
    public ModelBase() {
        this.id = RandomUtil.ssid();
        this.createTime = new Date();
        this.updateTime = new Date();
    }

    protected ID id;

    //数据创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date createTime;

    //最后更新时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date updateTime;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
