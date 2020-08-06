package com.owl.mvc.dao;

import com.owl.mvc.so.IdListSO;
import com.owl.mvc.so.IdSO;
import com.owl.mvc.so.ModelListSO;
import com.owl.mvc.so.ModelSO;

import java.util.List;

/**
 * 關係數據類型dao，本接口對外開發
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/10/10.
 */
public interface RelationBaseDao<T, ID> {


    /**
     * 直接插入
     * @param model 泛型对象
     * @return int
     */
    int insert(T model);

    /**
     * 批量插入
     * @param modelListSO 内含汎型對象
     * @return int
     */
    int insertList(ModelListSO<T> modelListSO);


    /**
     * 物理 刪除
     * @param idSO 泛型对象
     * @return int
     */
    int deleteByPrimaryKeyRe(IdSO<ID> idSO);

    /**
     * 物理 批量刪除
     * @param idListSO 内含id集合
     * @return int
     */
    int deleteByPrimaryKeyListRe(IdListSO<ID> idListSO);

    /**
     * 物理 刪除
     * @param modelSO 泛型对象
     * @return int
     */
    int deleteBySelectiveRe(ModelSO<T> modelSO);

    /**
     * 查詢是否存在
     * @param modelSO 内含汎型對象
     * @return list
     */
    List<T> selectByExact(ModelSO<T> modelSO);
}
