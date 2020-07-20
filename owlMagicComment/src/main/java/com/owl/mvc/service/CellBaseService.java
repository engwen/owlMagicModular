package com.owl.mvc.service;

import com.owl.mvc.dto.ModelDTO;
import com.owl.mvc.dto.PageDTO;
import com.owl.mvc.function.CountListLamda;
import com.owl.mvc.function.ListByPageLamda;
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
public interface CellBaseService<T, ID> {
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
    MsgResultVO deleteRe(T model);

    MsgResultVO deleteByIdRe(ID id);

    /**
     * 物理刪除
     * @param idList ID集合
     * @return 汎型對象
     */
    MsgResultVO deleteByIdListRe(List<ID> idList);


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

    /**
     * 查詢指定集合
     * @param idListSO 内含汎型對象
     * @return list
     */
    MsgResultVO<List<T>> selectByIdList(IdListSO<ID> idListSO);

    MsgResultVO<List<T>> selectByIdList(List<ID> idList);

    /**
     * 檢查数据是否存在
     * @param model 检索条件
     * @return Boolean
     */
    MsgResultVO<?> isExist(T model);


    default <T, M> PageVO<T> buildPageVO(PageDTO<M> pageDTO, CountListLamda<M> countList, ListByPageLamda<T, M> resultList) {
        PageVO<T> pageVO = new PageVO<>();
        SelectLikeSO<M> selectLikeSO = SelectLikeSO.getInstance(pageDTO);
        pageVO.initPageVO(countList.countSumList(selectLikeSO), pageDTO.getRequestPage(), pageDTO.getRows(), pageDTO.getGetAll());
        selectLikeSO.setRows(pageVO.getRows());
        selectLikeSO.setUpLimit(pageVO.getUpLimit());
        pageVO.setResultData(resultList.selectPageList(selectLikeSO));
        return pageVO.successResult(MsgConstant.REQUEST_SUCCESS);
    }
}
