package com.owl.mvc.service;

import com.owl.mvc.dao.RelationBaseDao;
import com.owl.mvc.dto.RelationDTO;
import com.owl.mvc.model.ModelBase;
import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.so.IdListSO;
import com.owl.mvc.so.IdSO;
import com.owl.mvc.so.ModelListSO;
import com.owl.mvc.so.ModelSO;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.util.DateCountUtil;
import com.owl.util.RandomUtil;
import com.owl.util.RegexUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.beans.Transient;
import java.util.List;
import java.util.stream.Collectors;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/04/22.
 */
public abstract class RelationBaseServiceAb<M extends RelationBaseDao<T, ID>, T extends ModelBase<ID>, ID> implements RelationBaseService<T, ID> {
    @Autowired
    protected M relationBaseDao;

    /**
     * 插入關係數據
     * @param model 汎型對象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO<?> insert(T model) {
        List<T> list = relationBaseDao.selectByExact(ModelSO.getInstance(model));
        if (list.size() > 0) {
            return MsgResultVO.error(MsgConstant.REQUEST_IS_EXITS);
        } else {
            if (RegexUtil.isEmpty(model.getId())) {
                model.setId(RandomUtil.ssidStr());
            }
            model.setCreateTime(DateCountUtil.getYMDHMS());
            model.setUpdateTime(DateCountUtil.getYMDHMS());
            relationBaseDao.insert(model);
            return MsgResultVO.success();
        }
    }

    @Override
    @Transient
    public MsgResultVO<?> updateList(RelationDTO<T> relationDTO) {
        return this.updateList(relationDTO.getModel(), relationDTO.getModelList());
    }

    @Override
    @Transient
    public MsgResultVO<?> updateList(T oldModel, List<T> modelList) {
        if (null != oldModel) {
            relationBaseDao.deleteBySelectiveRe(ModelSO.getInstance(oldModel));
        }
        if (null != modelList && modelList.size() > 0) {
            List<T> collect = modelList
                    .stream()
                    .filter(it -> relationBaseDao.selectByExact(ModelSO.getInstance(it)).size() == 0)
                    .peek(it -> {
                        it.setId(RandomUtil.ssidStr());
                        it.setCreateTime(DateCountUtil.getYMDHMS());
                    })
                    .collect(Collectors.toList());
            if (collect.size() > 0) {
                relationBaseDao.insertList(ModelListSO.getInstance(collect));
            }
        }
        return MsgResultVO.success();
    }


    /**
     * 刪除關係數據
     * @param model 汎型對象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO<?> delete(T model) {
        relationBaseDao.deleteBySelectiveRe(ModelSO.getInstance(model));
        return MsgResultVO.success();
    }

    @Override
    public MsgResultVO<?> deleteByPrimaryKeyRe(ID id) {
        relationBaseDao.deleteByPrimaryKeyRe(IdSO.getInstance(id));
        return MsgResultVO.success();
    }

    @Override
    public MsgResultVO<?> deleteByPrimaryKeyListRe(List<ID> idList) {
        relationBaseDao.deleteByPrimaryKeyListRe(IdListSO.getInstance(idList));
        return MsgResultVO.success();
    }


    /**
     * 查詢
     * @param model d idList
     * @return 基礎數據
     */
    @Override
    public MsgResultVO<List<T>> select(T model) {
        return MsgResultVO.success(relationBaseDao.selectByExact(ModelSO.getInstance(model)));
    }


}
