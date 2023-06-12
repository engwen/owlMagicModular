package com.owl.comment.annotations;

import java.lang.annotation.*;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2023/6/9.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OwlExport {
}
