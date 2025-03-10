package com.owl.jdbc.utils;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2022/12/30.
 */
public class PageUtil {
    public static Integer countPageDownLimit(Integer sum, Integer upLimit, Integer size) {
        Integer downLimit = null;
        if (upLimit < sum) {
            downLimit = upLimit + size;
            if (downLimit > sum) {
                downLimit = sum;
            }
        }
        return downLimit;
    }
}
