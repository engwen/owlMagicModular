package com.owl.mvc.controller;

import com.owl.mvc.dto.RelationDTO;
import com.owl.mvc.vo.MsgResultVO;

/**
 * 用于定义controller基础类
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/07/16.
 */
public interface RelationBaseController<T> {

    /**
     * 批量更新
     * @param relationDTO 将要被更新的对象
     * @return 结果
     */
    MsgResultVO<?> updateRelationList(RelationDTO<T> relationDTO);
}
