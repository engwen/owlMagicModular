package com.owl.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 樹狀數據工具基本類，提供自身id，父類id，子類集合的功能，方便使用工具類獲取指定集合
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/12/3.
 */
public abstract class TreeBase<T extends TreeBase<T>> extends ModelPrototype {
    //自id
    private Long id;
    //父id
    private Long pid;
    //名称
    private String name;
    //子集合
    private List<T> treeList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public List<T> getTreeList() {
        return treeList;
    }

    public void setTreeList(List<T> treeList) {
        this.treeList = treeList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
