package com.owl.comment.asImpl;


import com.owl.comment.annotations.OwlTry;
import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.util.LogUtil;
import com.owl.util.RegexUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Order(91)
public class OwlTryAS {

    @Pointcut("@within(com.owl.comment.annotations.OwlTry) || @annotation(com.owl.comment.annotations.OwlTry)")
    public void setTryCut() {
    }

    @Around("setTryCut()")
    public Object tryCut(ProceedingJoinPoint joinPoint) {
        MsgResultVO<?> result = new MsgResultVO<>();
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            String value = methodSignature.getMethod().getAnnotation(OwlTry.class).value();
            if (!RegexUtil.isEmpty(value)) {
                LogUtil.error(value);
            }
            result.errorResult(MsgConstant.TRY_CATCH_THROWABLE_ERROR);
            e.printStackTrace();
        }
        return result;
    }
}
