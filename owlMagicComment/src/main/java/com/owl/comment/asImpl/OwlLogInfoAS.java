package com.owl.comment.asImpl;

import com.owl.comment.annotations.OwlLogInfo;
import com.owl.util.LoggerUtil;
import com.owl.util.RegexUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 使用切片管理controller層的日志
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/10/16.
 */
@Aspect
@Component
@Order(90)
public class OwlLogInfoAS {

    @Pointcut("@within(com.owl.comment.annotations.OwlLogInfo) || @annotation(com.owl.comment.annotations.OwlLogInfo)")
    public void logCut() {
    }

    @Before("logCut()")
    public void logInfo(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        OwlLogInfo logInfo = methodSignature.getMethod().getAnnotation(OwlLogInfo.class);

        if (null == logInfo) {
            logInfo = AnnotationUtils.findAnnotation(methodSignature.getMethod().getDeclaringClass(), OwlLogInfo.class);
        }
        //方法注解
        if (null != logInfo && !RegexUtil.isEmpty(logInfo.value())) {
            LoggerUtil.info(logInfo.value());
        } else {
//          joinPoint.getSignature().getDeclaringTypeName(),
            LoggerUtil.info(String.format("当前方法 %s", joinPoint.getSignature().getName()));
        }
    }

    @After("logCut()")
    public void logEndTime(JoinPoint joinPoint) {
        LoggerUtil.info(joinPoint.getSignature().getName() + "方法执行结束");
    }
}
