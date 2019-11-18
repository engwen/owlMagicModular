package com.owl.pattern.function;

import com.owl.pattern.function.base.OwlListenCodeBase;

/**
 * 无参数待执行代码对象
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/7/12.
 */
@FunctionalInterface
public interface ListenCodeNoParams extends OwlListenCodeBase {
    void startDoing();
}
