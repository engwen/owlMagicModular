package com.owl.comment.asImpl;

import com.owl.comment.annotations.OwlSetNullData;
import com.owl.mvc.utils.SpringServletContextUtil;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.mvc.vo.PageVO;
import com.owl.util.ClassTypeUtil;
import com.owl.util.LogUtil;
import com.owl.util.ObjectUtil;
import com.owl.util.RegexUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Logger;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2018/11/20.
 */
@Aspect
@Component
@Order(95)
public class OwlSetNullDataAS {
    private static final Logger logger = Logger.getLogger(OwlSetNullDataAS.class.getName());

    @Pointcut("@annotation(com.owl.comment.annotations.OwlSetNullData)")
    public void setNullDataCut() {
    }

    @Around("setNullDataCut()")
    public Object setNullData(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] setNullParams = methodSignature.getMethod().getAnnotation(OwlSetNullData.class).paramsValue();
        String[] setNullDatas = methodSignature.getMethod().getAnnotation(OwlSetNullData.class).backValue();
        if (setNullParams.length > 0) {
            OwlSetNullDataAS.setRequestParamsNull(setNullParams);
            OwlSetNullDataAS.setBodyParamsNull(joinPoint, setNullParams);
        }
        Object obj = joinPoint.proceed();
        if (setNullDatas.length > 0) {
            OwlSetNullDataAS.setBackNullByObject(setNullDatas, obj);
        }
        return obj;
    }

    /*
     * 设置 head 请求参数为空
     * @param setNullParams
     */
    private static void setRequestParamsNull(String[] setNullParams) {
        //此處從requestHead頭中獲取參數，@Param
        HttpServletRequest request = SpringServletContextUtil.getRequest();
        Map<String, String[]> paramsHeadMap = request.getParameterMap();
        for (String param : setNullParams) {
            paramsHeadMap.put(param, null);
        }
    }

    /**
     * 设置 body 请求参数为空
     * @param joinPoint 切面
     * @throws IllegalAccessException 异常
     */
    private static void setBodyParamsNull(ProceedingJoinPoint joinPoint, String[] setNullParams) throws Throwable {
        //检查requestBody
        Object[] args = joinPoint.getArgs();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        //参数注解，1维是参数，2维是注解
        Annotation[][] annotations = method.getParameterAnnotations();
        //开始处理参数
        for (int i = 0; i < annotations.length; i++) {
            Object paramsVO = args[i];
            //获取注解数组
            Annotation[] paramAnn = annotations[i];
            //参数为空，直接下一个参数
            if (paramsVO == null || paramAnn.length == 0) {
                continue;
            }
            boolean isBodyOb = Arrays.stream(paramAnn).anyMatch(it -> it.annotationType().equals(RequestBody.class));
            if (isBodyOb) {
                //从接收封装的对象
                if (ClassTypeUtil.isPackClass(paramsVO) || ClassTypeUtil.isBaseClass(paramsVO)) {
                    LogUtil.error("OwlSetNullDataAS 此注解仅接收 Map 或 Object 对象");
                } else {
//                使用Map接收参数
                    if (paramsVO instanceof Map) {
                        Map<String, Object> paramsBodyMap = (Map<String, Object>) paramsVO;
                        for (String param : setNullParams) {
                            paramsBodyMap.put(param, null);
                        }
                    } else {
//                  使用对象接收参数
                        Field[] fields = ObjectUtil.getSupperClassProperties(paramsVO, new Field[0]);
                        for (Field field : fields) {
                            for (String param : setNullParams) {
                                if (field.getName().equals(param)) {
                                    field.setAccessible(true);
                                    String methodName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                                    Method setMethod = paramsVO.getClass().getMethod(methodName, field.getType());
                                    OwlSetNullDataAS.setNullByField(setMethod, paramsVO, field.getType());
                                }
                            }
                        }
                    }
                }
                break;
            }
        }
    }


    private static void setBackNullByObject(String[] setNullDatas, Object resultDataObj) throws Exception {
        Field[] fields = ObjectUtil.getSupperClassProperties(resultDataObj, new Field[0]);
        if (!RegexUtil.isEmpty(resultDataObj)) {
            if (ClassTypeUtil.isBaseClass(resultDataObj) || ClassTypeUtil.isPackClass(resultDataObj)) {
                logger.warning("OwlSetNullDataAS 此注解不支持基本类型及其包装类");
            } else if (resultDataObj instanceof PageVO) {
                PageVO<?> pageVO = (PageVO<?>) resultDataObj;
                Object obj = pageVO.getResultData();
                if (!RegexUtil.isEmpty(obj)) {
                    OwlSetNullDataAS.setBackNullByObject(setNullDatas, obj);
                }
            } else if (resultDataObj instanceof MsgResultVO) {
                MsgResultVO<?> resultVO = (MsgResultVO<?>) resultDataObj;
                Object obj = resultVO.getResultData();
                if (!RegexUtil.isEmpty(obj)) {
                    OwlSetNullDataAS.setBackNullByObject(setNullDatas, obj);
                }
            } else if (resultDataObj instanceof Collection) {
                OwlSetNullDataAS.setNullByList(setNullDatas, resultDataObj);
            } else if (resultDataObj instanceof Map) {
                OwlSetNullDataAS.setNullByMap(setNullDatas, resultDataObj);
            } else {
                for (Field field : fields) {
                    for (String param : setNullDatas) {
                        if (param.equals(field.getName())) {
                            field.setAccessible(true);
                            String methodName = "set" + param.substring(0, 1).toUpperCase() + param.substring(1);
                            Method method = resultDataObj.getClass().getMethod(methodName, field.getType());
                            OwlSetNullDataAS.setNullByField(method, resultDataObj, field.getType());
                        }
                    }
                }
            }
        }
    }

    private static void setNullByList(String[] setNullDatas, Object resultDataObj) throws Exception {
        List<?> temp = (List<?>) resultDataObj;
        Field[] fields = ObjectUtil.getSupperClassProperties(temp.get(0), new Field[0]);
        for (Field field : fields) {
            for (String param : setNullDatas) {
                if (param.equals(field.getName())) {
                    field.setAccessible(true);
                    for (Object objTemp : temp) {
                        String methodName = "set" + param.substring(0, 1).toUpperCase() + param.substring(1);
                        Method method = temp.get(0).getClass().getMethod(methodName, field.getType());
                        OwlSetNullDataAS.setNullByField(method, objTemp, field.getType());
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void setNullByMap(String[] setNullDatas, Object resultDataObj) {
        Map<String, Object> temp = (Map<String, Object>) resultDataObj;
        for (String param : setNullDatas) {
            if (ClassTypeUtil.isBaseClass(temp.get(param))) {
                logger.warning("OwlSetNullDataAS 此注解不支持基本类型及其包装类");
            } else {
                temp.put(param, null);
            }
        }
    }

    private static void setNullByField(Method method, Object obj, Class className) throws Exception {
        if (className.equals(String.class)) {
            method.invoke(obj, (String) null);
        } else if (className.equals(Long.class)) {
            method.invoke(obj, (Long) null);
        } else if (className.equals(Integer.class)) {
            method.invoke(obj, (Integer) null);
        } else if (className.equals(Float.class)) {
            method.invoke(obj, (Float) null);
        } else if (className.equals(Double.class)) {
            method.invoke(obj, (Double) null);
        } else if (className.equals(List.class)) {
            method.invoke(obj, (List) null);
        } else if (className.equals(Date.class)) {
            method.invoke(obj, (Date) null);
        } else {
            logger.warning(className + "OwlSetNullDataAS 类型不支持");
        }
    }
}
