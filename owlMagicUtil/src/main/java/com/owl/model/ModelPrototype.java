package com.owl.model;


import com.owl.util.ObjectUtil;

import java.io.Serializable;
import java.util.Map;

/**
 * 返回基礎信息類，該類不可實例化直接使用
 * author engwen
 * email xiachanzou@outlook.com
 * 2017/10/23.
 */
public abstract class ModelPrototype implements Serializable {
    //序列化支持
    protected static final long serialVersionUID = -6985636285761991122L;

    /**
     * 为了方便查看结果信息，直接使用JSON格式
     * @return 字符串
     */
    public String toJSON() {
        return ObjectUtil.toJSON(this);
    }

    /**
     * 为了方便转换结果信息，直接使用Map对象
     * @return map对象
     */
    public Map<String, Object> toMap() {
        return ObjectUtil.toMap(this);
    }

    @Override
    public String toString() {
        return toJSON();
    }

    public void print() {
        System.out.println(toJSON());
    }
}
