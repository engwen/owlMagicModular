package com.owl.mvc.controller;

import com.owl.mvc.dto.ModelDTO;
import com.owl.mvc.dto.PageDTO;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.mvc.vo.PageVO;

import java.util.List;

/**
 * 用于定义controller基础类
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/07/16.
 */
interface CellBaseController<T, ID> {

    /**
     * 创建
     * @param model 将要被创建的对象
     * @return 创建后的对象返回数据
     */
    MsgResultVO<T> create(T model);

    /**
     * 批量创建
     * @param list 待创建对象集合
     * @return 结果
     */
    MsgResultVO<?> createList(List<T> list);

    /**
     * 删除功能
     * @param model 待删除的对象
     * @return 结果
     */
    MsgResultVO<?> deleteRe(T model);

    /**
     * 批量删除
     * @param deleteDTO 删除对象DTO
     * @return 结果
     */
    MsgResultVO<?> deleteListRe(List<ID> deleteDTO);

    /**
     * 全量更新
     * @param model 将要被更新的对象
     * @return 结果
     */
    MsgResultVO<?> update(T model);

    MsgResultVO<?> updateByNotNull(T model);

    /**
     * 获取详情
     * @param model 获取详情的对象唯一属性
     * @return 结果对象
     */
    MsgResultVO<T> details(T model);


    /**
     * 获取所有对象
     * @param model 检索条件
     * @return 结果集合
     */

    MsgResultVO<List<T>> list(T model);

    /**
     * 获取分页集合
     * @param pageDTO 请求分页对象
     * @return 分页集合
     */
    PageVO<T> listByPage(PageDTO<T> pageDTO);

    /**
     * 获取所有对象
     * @param modelDTO 检索条件
     * @return 结果集合
     */
    MsgResultVO<List<T>> listByPage(ModelDTO<T> modelDTO);

    /**
     * 檢查数据是否存在
     * @param model 检索条件
     * @return Boolean
     */
    MsgResultVO<?> isExist(T model);
}
