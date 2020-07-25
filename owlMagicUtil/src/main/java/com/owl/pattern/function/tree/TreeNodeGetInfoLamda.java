package com.owl.pattern.function.tree;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2020/7/24.
 */
@FunctionalInterface
public interface TreeNodeGetInfoLamda<T, ID> {
    ID getInfo(T node);
}
