package com.owl.comment.annotations;

import java.lang.annotation.*;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2021/4/24.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OwlLogInfo {
    String value() default "";
}
