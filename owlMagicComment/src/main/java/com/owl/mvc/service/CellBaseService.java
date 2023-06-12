package com.owl.mvc.service;

import com.owl.mvc.dto.ModelDTO;
import com.owl.mvc.dto.PageDTO;
import com.owl.mvc.function.page.CountListLambda;
import com.owl.mvc.function.page.ListByPageLambda;
import com.owl.mvc.model.ModelBase;
import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.so.IdListSO;
import com.owl.mvc.so.IdSO;
import com.owl.mvc.so.SelectLikeSO;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.mvc.vo.PageVO;

import java.util.List;

/**
 * 不推薦的使用方式，采取包内策略，详见CellBaseServiceAb类 {@link CellBaseServiceAb}
 * 大多數的默認接口
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/01/22.
 */
public interface CellBaseService<T extends ModelBase<ID>, ID> {
    /**
     * 創建
     * @param model 汎型對象
     * @return 汎型對象
     */
    MsgResultVO<T> create(T model);

    /**
     * 批量創建
     * @param modelList 汎型對象
     * @return 汎型對象
     */
    MsgResultVO<?> createList(List<T> modelList);

    /**
     * 物理刪除
     * @param model 對象
     * @return 汎型對象
     */
    MsgResultVO<?> deleteRe(T model);

    /**
     * 物理刪除
     * @param id 對象
     * @return 汎型對象
     */
    MsgResultVO<?> deleteByIdRe(ID id);

    /**
     * 物理批量刪除
     * @param idList ID集合
     * @return 汎型對象
     */
    MsgResultVO<?> deleteByIdListRe(List<ID> idList);

    /**
     * 全部属性更新
     * @param model 汎型對象
     * @return 基礎數據
     */
    MsgResultVO<?> update(T model);

    /**
     * 增量属性更新
     * @param model 汎型對象
     * @return 基礎數據
     */
    MsgResultVO<?> updateByNotNull(T model);


    /**
     * 獲取詳情
     * @param id id
     * @return 汎型對象
     */
    MsgResultVO<T> detailsById(ID id);

    /**
     * 獲取詳情
     * @param idSO id
     * @return 汎型對象
     */
    MsgResultVO<T> detailsById(IdSO<ID> idSO);

    /**
     * 獲取詳情
     * @param model 汎型對象檢索條件
     * @return 汎型對象
     */
    MsgResultVO<T> details(T model);


    /**
     * 獲取分頁列表，添加 model 提供檢索功能
     * @param getAll      是否獲取所有
     * @param requestPage 請求頁數
     * @param rows        請求列表的尺寸
     * @param model       檢索條件
     * @return 分頁對象
     */
    PageVO<T> list(Boolean getAll, Integer requestPage, Integer rows, T model);

    PageVO<T> list(PageDTO<T> pageDTO);

    /**
     * 獲取所有的對象，添加 model 提供檢索功能,精确查询
     * @param modelDTO 检索条件
     * @return 對象集合
     */
    MsgResultVO<List<T>> listByExact(ModelDTO<T> modelDTO);

    MsgResultVO<List<T>> listByExact();

    /**
     * 查詢指定集合
     * @param idListSO 内含汎型對象
     * @return list
     */
    MsgResultVO<List<T>> listByIdList(IdListSO<ID> idListSO);

    MsgResultVO<List<T>> listByIdList(List<ID> idList);

    /**
     * 檢查数据是否存在
     * @param model 检索条件
     * @return Boolean
     */
    MsgResultVO<?> isExist(T model);

    /**
     * 默认将查询结果生成分页对象，支持使用相关类获取，M 为请求参数泛型，T 为实际结果泛型
     * @param pageDTO    界面接收的请求参数
     * @param countList  计算总数
     * @param resultList 查询sql
     * @param <M>        泛型
     * @return 分页对象
     */
    default <M> PageVO<T> buildPageVO(PageDTO<M> pageDTO, CountListLambda<M> countList, ListByPageLambda<T, M> resultList) {
        PageVO<T> pageVO = new PageVO<>();
        SelectLikeSO<M> selectLikeSO = SelectLikeSO.getInstance(pageDTO);
        pageVO.initPageVO(countList.countSumList(selectLikeSO), pageDTO.getRequestPage(), pageDTO.getRows(), pageDTO.getGetAll());
        selectLikeSO.setRows(pageVO.getRows());
        selectLikeSO.setUpLimit(pageVO.getUpLimit());
        selectLikeSO.setDownLimit(pageVO.getDownLimit());
        pageVO.setResultData(resultList.selectPageList(selectLikeSO));
        return pageVO.successResult(MsgConstant.REQUEST_SUCCESS);
    }
}
