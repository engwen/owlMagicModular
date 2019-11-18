package com.owl.pattern.function;

import com.owl.pattern.function.base.OwlListenCodeBase;

/**
 * 有参数待执行代码对象
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/10/28.
 */
@FunctionalInterface
public interface ListenCodeParams extends OwlListenCodeBase {
    void startDoing(Object... params);
}
