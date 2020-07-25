package com.owl.mvc.function.page;

import com.owl.mvc.so.SelectLikeSO;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2020/7/15.
 */
@FunctionalInterface
public interface CountListLamda<T> {
    Integer countSumList(SelectLikeSO<T> pageDTO);
}
