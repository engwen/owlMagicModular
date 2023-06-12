package com.owl.mvc.function;

import java.io.Serializable;
import java.util.function.Function;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2020/7/15.
 */
@FunctionalInterface
public interface SFunction<T, R> extends Function<T, R>, Serializable {
}
