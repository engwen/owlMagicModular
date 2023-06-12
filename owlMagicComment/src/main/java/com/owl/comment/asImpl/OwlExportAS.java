package com.owl.comment.asImpl;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.owl.mvc.dto.PageDTO;
import com.owl.mvc.model.ModelBase;
import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.util.FieldUtil;
import io.swagger.annotations.ApiModelProperty;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2018/12/7.
 */
@Aspect
@Component
@Order(97)
public class OwlExportAS {
    private final Logger logger = LoggerFactory.getLogger(OwlExportAS.class);

    @Pointcut("@within(com.owl.comment.annotations.OwlExport) || @annotation(com.owl.comment.annotations.OwlExport)")
    public void exportClassCut() {
    }

    @AfterThrowing(value = "exportClassCut()", throwing = "ex")
    public Object afterThrowing(JoinPoint joinPoint, Exception ex) {
        MsgResultVO<String> result = new MsgResultVO<>();
        result.errorResult(MsgConstant.CONTROLLER_THROWABLE_ERROR);
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        logger.error(methodSignature.getMethod().getName() + "      " + ex);
        return result;
    }

    @Around("exportClassCut()")
    public Object exportClass(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();
        Object arg = args[0];
        if (arg instanceof PageDTO) {
            if (((PageDTO<?>) arg).isExport()) {

            }
        }

        Object obj = joinPoint.proceed();
        if (obj instanceof MsgResultVO) {
            Boolean result = ((MsgResultVO<?>) obj).getResult();
            if (result) {

            }
        }
        Object outPrt = null;
        if (obj instanceof List) {
            outPrt = ((List<?>) obj).get(0);
            if (outPrt instanceof ModelBase) {
                List<String> keys = new ArrayList<>();
                Field[] fields = FieldUtil.getSupperClassProperties(outPrt);
                for (Field field : fields) {
                    ApiModelProperty api = field.getAnnotation(ApiModelProperty.class);
                    if (null != api && null != api.value()) {
                        keys.add(api.value());
                    }
                }
                //在内存操作，写到浏览器
                ExcelWriter writer = ExcelUtil.getWriter(true);

            }
        } else {
            logger.error("只支持List集合导出，单属性不支持导出");
        }
        return obj;
    }
}
