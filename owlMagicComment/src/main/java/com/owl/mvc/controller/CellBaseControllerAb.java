package com.owl.mvc.controller;

import com.owl.mvc.dto.ModelDTO;
import com.owl.mvc.dto.PageDTO;
import com.owl.mvc.model.ModelBase;
import com.owl.mvc.service.CellBaseServiceAb;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.mvc.vo.PageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 本类可用于Controller的集成，定义了常用的部分功能，继承本类后实现即可
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/07/16.
 */
@RestController
public abstract class CellBaseControllerAb<M extends CellBaseServiceAb<?, T, ID>, T extends ModelBase<ID>, ID> implements CellBaseController<T, ID> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected M cellBaseServiceAb;

    /**
     * 创建
     * @param model 将要被创建的对象
     * @return 创建后的对象返回数据
     */
    @Override
    public MsgResultVO<T> create(T model) {
        logger.info("default create");
        return cellBaseServiceAb.create(model);
    }

    /**
     * 批量创建
     * @param list 待创建对象集合
     * @return 结果
     */
    @Override
    public MsgResultVO<?> createList(List<T> list) {
        logger.info("default createList");
        return cellBaseServiceAb.createList(list);
    }


    /**
     * 删除功能
     * @param model 待删除的对象
     * @return 结果
     */
    @Override
    public MsgResultVO<?> deleteRe(T model) {
        logger.info("default real delete");
        return cellBaseServiceAb.deleteRe(model);
    }

    /**
     * 批量删除
     * @param idList 删除对象DTO
     * @return 结果
     */
    @Override
    public MsgResultVO<?> deleteListRe(List<ID> idList) {
        logger.info("default real delete");
        return cellBaseServiceAb.deleteByIdListRe(idList);
    }

    /**
     * 全量更新
     * @param model 将要被更新的对象
     * @return 结果
     */
    @Override
    public MsgResultVO<?> update(T model) {
        logger.info("default update");
        return cellBaseServiceAb.update(model);
    }

    /**
     * 增量更新
     * @param model 将要被更新的对象
     * @return 结果
     */
    @Override
    public MsgResultVO<?> updateByNotNull(T model) {
        logger.info("default update");
        return cellBaseServiceAb.updateByNotNull(model);
    }


    /**
     * 获取详情
     * @param model 获取详情的对象唯一属性
     * @return 结果对象
     */
    @Override
    public MsgResultVO<T> details(T model) {
        logger.info("default details");
        return cellBaseServiceAb.details(model);
    }

    /**
     * 获取分页集合
     * @param pageDTO 请求分页对象
     * @return 分页集合
     */
    @Override
    public PageVO<T> list(PageDTO<T> pageDTO) {
        logger.info("default list");
        return cellBaseServiceAb.list(pageDTO);
    }

    /**
     * 获取所有对象
     * @param modelDTO 检索条件
     * @return 结果集合
     */
    @Override
    public MsgResultVO<List<T>> list(ModelDTO<T> modelDTO) {
        logger.info("default list");
        return cellBaseServiceAb.listByExact(modelDTO);
    }

    /**
     * 檢查数据是否存在
     * @param model 检索条件
     * @return Boolean
     */
    @Override
    public MsgResultVO<?> isExist(T model) {
        logger.info("default isExist");
        return cellBaseServiceAb.isExist(model);
    }
}
