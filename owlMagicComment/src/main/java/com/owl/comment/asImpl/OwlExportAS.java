package com.owl.comment.asImpl;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.owl.mvc.dto.PageDTO;
import com.owl.mvc.model.ModelBase;
import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.mvc.vo.PageVO;
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
        Object obj = null;
        Object[] args = joinPoint.getArgs();
        Object arg = args[0];
        //判断参数是否符合条件
        if (arg instanceof PageDTO) {
            //是否需要导出
            if (((PageDTO<?>) arg).isExport()) {
                //拿到执行结果
                obj = joinPoint.proceed();
                //查看结果类型
                if (obj instanceof PageVO) {
                    PageVO<?> resultVO = (PageVO<?>) obj;
                    Boolean result = ((PageVO<?>) obj).getResult();
                    //结果正确,准备对结果进行处理
                    if (result) {
                        List<?> resultData = resultVO.getResultData();
                        if (resultData != null) {
                            Object outPrt = null;
                            outPrt = resultData.get(0);
                            if (outPrt instanceof ModelBase) {
                                List<String> keys = new ArrayList<>();
                                Field[] fields = FieldUtil.getSupperClassProperties(outPrt);
                                //在内存操作，写到浏览器
                                ExcelWriter writer = ExcelUtil.getWriter(true);
                                for (int i = 0; i < fields.length; i++) {
                                    Field field = fields[i];
                                    ApiModelProperty api = field.getAnnotation(ApiModelProperty.class);
                                    if (null != api && null != api.value()) {
                                        keys.add(api.value());
                                    } else {
                                        keys.add("");
                                    }
                                    writer.setColumnWidth(i, 5 + 5 * keys.get(i).length());
                                }
                            } else {
                                logger.error("只支持List集合导出，单属性不支持导出");
                            }
                        }

                    }
                }
            }
        } else {
            obj = joinPoint.proceed();
        }
        return obj;
    }
}
