package com.owl.comment.asImpl;

import com.owl.util.LogUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/1/30.
 */
@Aspect
@Component
@Order(90)
public class OwlCountTimeAS {
    private static final double ONE_MINUTE = 1000;
    private Date startTime;

    @Pointcut("@within(com.owl.comment.annotations.OwlCountTime) || @annotation(com.owl.comment.annotations.OwlCountTime)")
    public void countTimeCut() {
    }

    @Before("countTimeCut()")
    public void logStartTime(JoinPoint joinPoint) {
        startTime = new Date();
    }

    @After("countTimeCut()")
    public void logEndTime(JoinPoint joinPoint) {
        Double second = ((new Date()).getTime() - startTime.getTime()) / ONE_MINUTE;
        LogUtil.info(String.format("方法名称: %s 花费时间: %s 秒", joinPoint.getSignature().getName(), second));
    }
}
