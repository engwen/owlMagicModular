package com.owl.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 樹狀數據工具基本類，提供自身id，父類id，子類集合的功能，方便使用工具類獲取指定集合
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/12/3.
 */
public class TreeBase<T, ID> extends ModelPrototype {
    //自id
    private ID id;
    //父id
    private ID pid;
    //名称
    private String name;
    //节点
    private T node;
    //子集合
    private List<TreeBase<T, ID>> treeList = new ArrayList<>();

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public ID getPid() {
        return pid;
    }

    public void setPid(ID pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getNode() {
        return node;
    }

    public void setNode(T node) {
        this.node = node;
    }

    public List<TreeBase<T, ID>> getTreeList() {
        return treeList;
    }

    public void setTreeList(List<TreeBase<T, ID>> treeList) {
        this.treeList = treeList;
    }
}
