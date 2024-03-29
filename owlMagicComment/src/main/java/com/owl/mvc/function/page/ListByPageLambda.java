package com.owl.mvc.function.page;

import com.owl.mvc.so.SelectLikeSO;

import java.util.List;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2020/7/15.
 */
@FunctionalInterface
public interface ListByPageLambda<T, M> {
    List<T> selectPageList(SelectLikeSO<M> selectLikeSO);
}
