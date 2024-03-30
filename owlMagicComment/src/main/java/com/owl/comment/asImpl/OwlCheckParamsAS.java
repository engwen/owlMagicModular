package com.owl.comment.asImpl;

import com.owl.comment.annotations.OwlCheckParams;
import com.owl.comment.asModel.ParamsCheckStatus;
import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.utils.SpringServletContextUtil;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.util.ClassTypeUtil;
import com.owl.util.FieldUtil;
import com.owl.util.RegexUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 參數注解功能實現
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/10/15.
 */
@Aspect
@Component
@Order(91)
public class OwlCheckParamsAS {
    private static final Logger logger = LoggerFactory.getLogger(OwlCheckParamsAS.class);

    @Pointcut("@annotation(com.owl.comment.annotations.OwlCheckParams)")
    public void checkParamsCut() {
    }

    @Around("checkParamsCut()")
    public Object checkParams(ProceedingJoinPoint joinPoint) throws Throwable {
        MsgResultVO<?> result = new MsgResultVO<>();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        ParamsCheckStatus checkStatus = new ParamsCheckStatus();
        //獲取被標記不能爲空的屬性集合
        checkStatus.setParamsNotNull(methodSignature.getMethod().getAnnotation(OwlCheckParams.class).paramsNotNull());
        checkStatus.setParamsNotAllNull(methodSignature.getMethod().getAnnotation(OwlCheckParams.class).paramsNotAllNull());
        checkStatus.setBodyNotNull(methodSignature.getMethod().getAnnotation(OwlCheckParams.class).bodyNotNull());
        checkStatus.setBodyNotAllNull(methodSignature.getMethod().getAnnotation(OwlCheckParams.class).bodyNotAllNull());
        //獲取未設置為Post或Get，不能爲空和不能全部爲空
        String[] notNull = methodSignature.getMethod().getAnnotation(OwlCheckParams.class).notNull();
        String[] notAllNull = methodSignature.getMethod().getAnnotation(OwlCheckParams.class).notAllNull();
        if (notNull.length != 0 || notAllNull.length != 0) {
            //get方法
            if (SpringServletContextUtil.isGetRequest()) {
                checkStatus.setParamsNotNull(notNull);
                checkStatus.setParamsNotAllNull(notAllNull);
                //post方法
            } else if (SpringServletContextUtil.isPostRequest()) {
                checkStatus.setBodyNotNull(notNull);
                checkStatus.setBodyNotAllNull(notAllNull);
            }//其他方法暫時不考慮
        }
        //該注解沒有指定參數
        if (checkStatus.getParamsNotNull().length == 0 && checkStatus.getParamsNotAllNull().length == 0 && checkStatus.getBodyNotNull().length == 0 && checkStatus.getBodyNotAllNull().length == 0) {
            logger.info(MsgConstant.REQUEST_ANNOTATIONS_PARAMS_ERROR.getCode());
            return joinPoint.proceed(joinPoint.getArgs());
        }
        //检查requestParams
        checkRequestParams(joinPoint, checkStatus);
        //检查requestBody
        checkRequestBody(joinPoint, checkStatus);
        if (checkStatus.isParamsHasNull()) {
            result.errorResult(MsgConstant.REQUEST_PARAMETER_ERROR.getCode(), backStr("请求参数 %s 不能为null", checkStatus.getRequestParamsIsNull()));
            logger.error("requestParams " + result.getResultMsg());
            return result;
        } else if (checkStatus.getParamsNotAllNull().length > 0 && checkStatus.isParamsAllOrNull()) {
            result.errorResult(MsgConstant.REQUEST_PARAMETER_ERROR.getCode(), backStr("请求参数 %s 不能全部为null", Arrays.asList(checkStatus.getParamsNotAllNull())));
            logger.error("requestParams " + result.getResultMsg());
            return result;
        } else if (checkStatus.isBodyHasNull()) {
            result.errorResult(MsgConstant.REQUEST_PARAMETER_ERROR.getCode(), backStr("请求参数 %s 不能为null", checkStatus.getRequestBodyIsNull()));
            logger.error("requestBody " + result.getResultMsg());
            return result;
        } else if (checkStatus.getBodyNotAllNull().length > 0 && checkStatus.isBodyAllOrNull()) {
            result.errorResult(MsgConstant.REQUEST_PARAMETER_ERROR.getCode(), backStr("请求参数 %s 不能全部为null", Arrays.asList(checkStatus.getBodyNotAllNull())));
            logger.error("requestBody " + result.getResultMsg());
            return result;
        } else {
            logger.info("参数检查通过");
            return joinPoint.proceed(joinPoint.getArgs());
        }
    }

    /**
     * 检查 get 参数
     * @param joinPoint   切面
     * @param checkStatus 检查状态
     */
    private static void checkRequestParams(ProceedingJoinPoint joinPoint, ParamsCheckStatus checkStatus) {
        //此處從requestHead頭中獲取參數，@Param
        HttpServletRequest request = SpringServletContextUtil.getRequest();
        //检查requestParam
        Map<String, String[]> paramsHeadMap = request.getParameterMap();
        if (null != paramsHeadMap && paramsHeadMap.keySet().size() > 0) {
            Optional<Boolean> any = Arrays.stream(checkStatus.getParamsNotNull()).filter(param -> null == paramsHeadMap.get(param) || paramsHeadMap.get(param).length == 0).map(checkStatus::addNullParams).findAny();
            checkStatus.setParamsHasNull(any.isPresent());
            Optional<String> first = Arrays.stream(checkStatus.getParamsNotAllNull()).filter(param -> null != paramsHeadMap.get(param) && paramsHeadMap.get(param).length > 0).findFirst();
            checkStatus.setParamsAllOrNull(!first.isPresent());
        } else if (checkStatus.getParamsNotNull().length > 0 || checkStatus.getParamsNotAllNull().length > 0) {
            logger.error(" 没有找到 @RequestParam 注解字段");
        }
    }

    /**
     * 检查 Post 参数
     * @param joinPoint   切面
     * @param checkStatus 检查状态
     * @throws IllegalAccessException 异常
     */
    @SuppressWarnings("unchecked")
    private static void checkRequestBody(ProceedingJoinPoint joinPoint, ParamsCheckStatus checkStatus) throws IllegalAccessException {
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
                Map<String, Object> paramsBodyMap = new HashMap<>();
                if (ClassTypeUtil.isPackClass(paramsVO) || ClassTypeUtil.isBaseClass(paramsVO)) {
                    logger.error("此注解只接收 Map 或 Object 对象");
                    return;
                } else {
                    if (paramsVO instanceof List<?> && !((List<?>) paramsVO).isEmpty()) {
                        logger.info("list集合不为空");
                        return;
                    }
                    // 使用Map接收参数
                    if (paramsVO instanceof Map) {
                        paramsBodyMap = (Map<String, Object>) paramsVO;
                    } else {
                        //使用对象接收参数,获取对象的全部属性
                        Field[] fields = FieldUtil.getSupperClassProperties(paramsVO);
                        for (Field field : fields) {
                            field.setAccessible(true);
                            if (null != paramsBodyMap.get(field.getName())) {
                                //继承的类数据可能存在覆盖可能
                                continue;
                            }
                            paramsBodyMap.put(field.getName(), field.get(paramsVO));
                        }
                    }
                    //遍历Body集合
                    for (String paramName : checkStatus.getBodyNotNull()) {
                        if (RegexUtil.isEmpty(paramsBodyMap.get(paramName))) {
                            checkStatus.addNullBody(paramName);
                            checkStatus.setBodyHasNull(true);
                        }
                    }
                    for (String paramName : checkStatus.getBodyNotAllNull()) {
                        if (!RegexUtil.isEmpty(paramsBodyMap.get(paramName))) {
                            checkStatus.setBodyAllOrNull(false);
                            break;
                        }
                    }
                }
                break;
            }
        }
    }


    /**
     * 替換參數, 將參數指定為指定格式
     * @param str 字符串
     * @param arr 集合
     * @return 字符串
     */
    private static String backStr(String str, List<String> arr) {
        String temp = arr.toString();
        return String.format(str, temp.substring(1, temp.length() - 1));
    }


    /*
     * true 通过 false，不通过
     * @param paramsVO
     * @param notNull
     * @param notAllNull
     * @param <T>
     * @return
     * @throws Throwable
     */
    @SuppressWarnings("unchecked")
    private static <T> boolean checkListParams(T paramsVO, String[] notNull, String[] notAllNull) {
        Map<String, Object> paramsBodyMap = new HashMap<>();
        boolean allOrNull = true;
        //                使用Map接收参数
        if (paramsVO instanceof Map) {
            paramsBodyMap = (Map<String, Object>) paramsVO;
        } else {
//                  使用对象接收参数
            Field[] fields = FieldUtil.getSupperClassProperties(paramsVO);
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    if (null != paramsBodyMap.get(field.getName())) {
                        //继承的类数据可能存在覆盖可能
                        continue;
                    }
                    paramsBodyMap.put(field.getName(), field.get(paramsVO));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        //存在为空的
        for (String param : notNull) {
            if (RegexUtil.isEmpty(paramsBodyMap.get(param))) {
                return false;
            }
        }
        for (String param : notAllNull) {
            if (!RegexUtil.isEmpty(paramsBodyMap.get(param))) {
                allOrNull = false;
                break;
            }
        }
        return notAllNull.length > 0 && !allOrNull;
    }
}