package com.owl.util;

import java.util.List;
import java.util.Set;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2025/12/30.
 */
public class ListUtil {

    public static boolean isNotEmpty(List list){
        return list != null && !list.isEmpty();
    }

    public static boolean isEmpty(List list){
        return list == null || list.isEmpty();
    }

    public static boolean isNotEmpty(Set set){
        return set != null && !set.isEmpty();
    }

    public static boolean isEmpty(Set set){
        return set == null || set.isEmpty();
    }
}
