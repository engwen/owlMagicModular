package com.owl.comment.asImpl;

import com.owl.comment.annotations.OwlConsolePrint;
import com.owl.comment.annotations.OwlLogInfo;
import com.owl.comment.utils.AsConsoleConsoleUtil;
import com.owl.util.RegexUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;

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
        OwlLogInfo owlConsolePrint = methodSignature.getMethod().getAnnotation(OwlLogInfo.class);

        if (null == owlConsolePrint) {
            owlConsolePrint = AnnotationUtils.findAnnotation(methodSignature.getMethod().getDeclaringClass(), OwlLogInfo.class);
        }
        //方法注解
        if (null != owlConsolePrint && !RegexUtil.isEmpty(owlConsolePrint.value())) {
            AsConsoleConsoleUtil.info(joinPoint, owlConsolePrint.value());
        } else {
//          joinPoint.getSignature().getDeclaringTypeName(),
            AsConsoleConsoleUtil.info(joinPoint, String.format("当前方法 %s", joinPoint.getSignature().getName()));
        }
    }

    @After("logCut()")
    public void logEndTime(JoinPoint joinPoint) {
        AsConsoleConsoleUtil.info(joinPoint, joinPoint.getSignature().getName() + "方法执行结束");
    }
}
