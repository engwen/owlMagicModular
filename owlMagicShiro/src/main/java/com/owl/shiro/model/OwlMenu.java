package com.owl.shiro.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OwlMenu {
    private Long id;

    private String name;
    private String btnId;
    private Date createTime;

    private Date updateTime;

    //子菜單
    private List<? extends OwlMenu> menuList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBtnId() {
        return btnId;
    }

    public void setBtnId(String btnId) {
        this.btnId = btnId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public List<? extends OwlMenu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<? extends OwlMenu> menuList) {
        this.menuList = menuList;
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