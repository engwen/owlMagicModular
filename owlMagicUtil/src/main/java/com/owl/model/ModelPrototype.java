package com.owl.model;


import com.owl.util.ObjectUtil;

import java.io.Serializable;

/**
 * 返回基礎信息類，該類不可實例化直接使用
 * author engwen
 * email xiachanzou@outlook.com
 * 2017/10/23.
 */
public abstract class ModelPrototype implements Serializable {
    //序列化支持
    private static final long serialVersionUID = -6985636285761991122L;

    /**
     * 为了方便查看结果信息，直接使用JSON格式
     * @return 字符串
     */
    public String toJSON() {
        return ObjectUtil.toJSON(this);
    }

    @Override
    public String toString() {
        return toJSON();
    }

    public void print() {
        System.out.println(toJSON());
    }
}
