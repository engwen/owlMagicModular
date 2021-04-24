package com.owl.comment.annotations;

import java.lang.annotation.*;

/**
 * 添加參數注解
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/10/15.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OwlCheckParams {
    //requestParams中不全为空
    String[] paramsNotAllNull() default {};

    //requestParams中不为空
    String[] paramsNotNull() default {};

    //requestParams中可以为空，一般可以不使用
    String[] paramsCanNull() default {};

    //requestBody中不全部为空
    String[] bodyNotAllNull() default {};

    //requestBody中不能为空
    String[] bodyNotNull() default {};

    //requestBody中可以为空，一般可以不使用
    String[] bodyCanNull() default {};

    //请求中不全为空，程序判断POST或者get
    String[] notAllNull() default {};

    //请求中不空，程序判断POST或者get
    String[] notNull() default {};

    //请求中可以为空，一般可以不使用，程序判断POST或者get
    String[] canNull() default {};
}
