package com.owl.mvc.service;

import com.owl.mvc.dao.CellBaseDao;
import com.owl.mvc.dto.ModelDTO;
import com.owl.mvc.dto.PageDTO;
import com.owl.mvc.model.ModelBase;
import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.so.*;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.mvc.vo.PageVO;
import com.owl.util.DateCountUtil;
import com.owl.util.RandomUtil;
import com.owl.util.RegexUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 不可實例化，繼承者需要自己實現其中的方法，支持自定義以及添加方法
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/04/22.
 */
public abstract class CellBaseServiceAb<M extends CellBaseDao<T, ID>, T extends ModelBase<ID>, ID> implements CellBaseService<T, ID> {

    @Autowired
    protected M cellBaseDao;

    /**
     * 創建
     * @param model 汎型對象
     * @return 汎型對象
     */
    @Override
    public MsgResultVO<T> create(T model) {
        if (RegexUtil.isEmpty(model.getId())) {
            model.setId(RandomUtil.ssidStr());
        }
        model.setCreateTime(DateCountUtil.getYMDHMS());
        model.setUpdateTime(DateCountUtil.getYMDHMS());
        cellBaseDao.insert(model);
        return MsgResultVO.success(model);
    }

    /**
     * 批量創建
     * @param modelList 汎型對象
     * @return 汎型對象
     */
    @Override
    public MsgResultVO<?> createList(List<T> modelList) {
        modelList.forEach(it -> {
            if (RegexUtil.isEmpty(it.getId())) {
                it.setId(RandomUtil.ssidStr());
            }
            it.setCreateTime(DateCountUtil.getYMDHMS());
            it.setUpdateTime(DateCountUtil.getYMDHMS());
        });
        if (modelList.size() > 0) {
            cellBaseDao.insertList(ModelListSO.getInstance(modelList));
        }
        return MsgResultVO.success();
    }

    /**
     * 物理删除 更新前需要查询，因此可能返回对象为父类型
     * @param model 对象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO<?> deleteRe(T model) {
        cellBaseDao.deleteBySelectiveRe(ModelSO.getInstance(model));
        return MsgResultVO.success();
    }

    /**
     * 物理删除
     * @param id 对象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO<?> deleteByIdRe(ID id) {
        if (null == id) {
            return MsgResultVO.error(MsgConstant.REQUEST_PARAMETER_ERROR);
        }
        cellBaseDao.deleteByPrimaryKeyRe(new IdSO<>(id));
        return MsgResultVO.success();
    }

    /**
     * 批量物理删除 更新前需要查询，因此可能返回对象为父类型
     * @param idList ID集合
     * @return 基礎數據
     */
    @Override
    public MsgResultVO<?> deleteByIdListRe(List<ID> idList) {
        if (null == idList || idList.isEmpty()) {
            return MsgResultVO.error(MsgConstant.REQUEST_PARAMETER_ERROR);
        }
        IdListSO<ID> idListSO = new IdListSO<>(idList);
        cellBaseDao.deleteByPrimaryKeyListRe(idListSO);
        return MsgResultVO.success();
    }

    /**
     * 更新 更新前需要查询
     * @param model 汎型對象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO<?> update(T model) {
        if (null == model) {
            return MsgResultVO.error(MsgConstant.REQUEST_PARAMETER_ERROR);
        }
        model.setUpdateTime(DateCountUtil.getYMDHMS());
        cellBaseDao.updateByPrimaryKey(model);
        return MsgResultVO.success();
    }

    /**
     * 更新 更新前需要查询
     * @param model 汎型對象
     * @return 基礎數據
     */
    @Override
    public MsgResultVO<?> updateByNotNull(T model) {
        if (null == model) {
            return MsgResultVO.error(MsgConstant.REQUEST_PARAMETER_ERROR);
        }
        MsgResultVO<T> resultVO = new MsgResultVO<>();
        model.setUpdateTime(DateCountUtil.getYMDHMS());
        cellBaseDao.updateByPrimaryKeySelective(model);
        resultVO.successResult();
        return resultVO;
    }

    /**
     * 獲取詳情
     * @param id 汎型對象檢索條件
     * @return 汎型對象
     */
    @Override
    public MsgResultVO<T> detailsById(ID id) {
        if (null == id) {
            return MsgResultVO.error(MsgConstant.REQUEST_PARAMETER_ERROR);
        }
        return MsgResultVO.success(cellBaseDao.selectByPrimaryKey(new IdSO<>(id)));
    }

    @Override
    public MsgResultVO<T> detailsById(IdSO<ID> idSO) {
        return MsgResultVO.success(cellBaseDao.selectByPrimaryKey(idSO));
    }

    /**
     * 獲取詳情
     * @param model 汎型對象檢索條件
     * @return 汎型對象
     */
    @Override
    public MsgResultVO<T> details(T model) {
        MsgResultVO<T> resultVO = new MsgResultVO<>();
        List<T> temp = cellBaseDao.selectByExact(SelectLikeSO.getInstance(model));
        if (null == temp || temp.isEmpty()) {
            resultVO.errorResult(MsgConstant.REQUEST_NOT_EXITS);
        } else if (temp.size() == 1) {
            resultVO.successResult(temp.get(0));
        } else {
            System.out.println("there are list with details back, but you just want one");
            resultVO.errorResult(MsgConstant.REQUEST_BACK_ARE_LIST);
        }
        return resultVO;
    }

    /**
     * 獲取詳情
     * @param model 汎型對象檢索條件
     * @return 汎型對象
     */
    @Override
    public MsgResultVO<List<T>> list(T model) {
        MsgResultVO<List<T>> resultVO = new MsgResultVO<>();
        List<T> temp = cellBaseDao.selectByExact(SelectLikeSO.getInstance(model));
        resultVO.successResult(temp);
        return resultVO;
    }

    /**
     * 獲取分頁列表，添加 model 提供檢索功能
     * @param pageDTO 请求对象
     * @return 分頁對象
     */
    @Override
    public PageVO<T> listByPage(PageDTO<T> pageDTO) {
        return this.buildPageVO(pageDTO,
                (selectLikeSO) -> this.cellBaseDao.countSumByCondition(selectLikeSO),
                (selectLikeSO) -> this.cellBaseDao.listByCondition(selectLikeSO)
        );
    }

    /**
     * 獲取分頁列表，添加 model 提供檢索功能
     * @param getAll      是否獲取所有
     * @param requestPage 請求頁數
     * @param rows        請求列表的尺寸
     * @param model       檢索條件
     * @return 分頁對象
     */
    @Override
    public PageVO<T> listByPage(Boolean getAll, Integer requestPage, Integer rows, T model) {
        PageVO<T> pageVO = new PageVO<>();
        pageVO.initPageVO(cellBaseDao.countSumByCondition(SelectLikeSO.getInstance(model)), requestPage, rows, getAll);
        pageVO.setResultData(cellBaseDao.listByCondition(SelectLikeSO.getInstance(model, pageVO.getUpLimit(), pageVO.getDownLimit(), pageVO.getRows())));
        return pageVO.successResult(MsgConstant.REQUEST_SUCCESS);
    }

    /**
     * 查詢指定集合
     * @param idListSO 内含汎型對象
     * @return list
     */
    @Override
    public MsgResultVO<List<T>> listByIdList(IdListSO<ID> idListSO) {
        return MsgResultVO.success(cellBaseDao.selectByPrimaryKeyList(idListSO));
    }

    @Override
    public MsgResultVO<List<T>> listByIdList(List<ID> idList) {
        return MsgResultVO.success(cellBaseDao.selectByPrimaryKeyList(IdListSO.getInstance(idList)));
    }

    /**
     * 精确查询，獲取所有符合条件的對象
     * @return 對象集合
     */
    @Override
    public MsgResultVO<List<T>> listByExact(ModelDTO<T> modelDTO) {
        if (modelDTO == null) {
            return listByExact();
        }
        SelectLikeSO<T> selectLikeSO = SelectLikeSO.getInstance(modelDTO);
        return MsgResultVO.success(cellBaseDao.selectByExact(selectLikeSO));
    }

    @Override
    public MsgResultVO<List<T>> listByExact() {
        SelectLikeSO<T> selectLikeSO = SelectLikeSO.getInstance((T) null);
        return MsgResultVO.success(cellBaseDao.selectByExact(selectLikeSO));
    }

    /**
     * 檢查数据是否存在
     * @param model 检索条件
     * @return Boolean
     */
    @Override
    public MsgResultVO<?> isExist(T model) {
        MsgResultVO<List<T>> resultVO = new MsgResultVO<>();
        List<T> list = cellBaseDao.selectByExact(SelectLikeSO.getInstance(model));
        if (!CollectionUtils.isEmpty(list)) {
            resultVO.successResult(list, MsgConstant.REQUEST_IS_EXITS);
        } else {
            resultVO.errorResult(MsgConstant.REQUEST_NOT_EXITS);
        }
        return resultVO;
    }
}
