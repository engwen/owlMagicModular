package com.owl.comment.asImpl;

import com.owl.comment.annotations.OwlCheckParams;
import com.owl.comment.utils.AsConsoleConsoleUtil;
import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.utils.SpringServletContextUtil;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.util.ClassTypeUtil;
import com.owl.util.ObjectUtil;
import com.owl.util.RegexUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
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

    @Pointcut("@annotation(com.owl.comment.annotations.OwlCheckParams)")
    public void checkParamsCut() {
    }

    @Around("checkParamsCut()")
    public Object checkParams(ProceedingJoinPoint joinPoint) throws Throwable {
        MsgResultVO<?> result = new MsgResultVO<>();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        獲取被標記不能爲空的屬性集合
        String[] notNull = methodSignature.getMethod().getAnnotation(OwlCheckParams.class).notNull();
        String[] notAllNull = methodSignature.getMethod().getAnnotation(OwlCheckParams.class).notAllNull();
        //該注解沒有指定參數
        if (notNull.length == 0 && notAllNull.length == 0) {
            AsConsoleConsoleUtil.info(joinPoint, MsgConstant.REQUEST_ANNOTATIONS_PARAMS_ERROR.getCode());
            return joinPoint.proceed(joinPoint.getArgs());
        }
//        其中是否存在空，默認不存在
        boolean hasNull = false;
        boolean allOrNull = true;
//        存放含有空的屬性
        List<String> paramsIsNull = new ArrayList<>();
//        此處從requestHead頭中獲取參數，@Param
        HttpServletRequest request = SpringServletContextUtil.getRequest();
//如果是GET請求
        if (SpringServletContextUtil.isGetRequest(request)) {
            Map<String, String[]> paramsHeadMap = request.getParameterMap();
            if (null != paramsHeadMap && paramsHeadMap.keySet().size() > 0) {
                for (String param : notNull) {
                    if (null == paramsHeadMap.get(param) || paramsHeadMap.get(param).length == 0) {
                        paramsIsNull.add(param);
                        hasNull = true;
                    }
                }
                for (String param : notAllNull) {
                    if (null != paramsHeadMap.get(param) && paramsHeadMap.get(param).length > 0) {
                        allOrNull = false;
                        break;
                    }
                }
            } else {
                AsConsoleConsoleUtil.error(joinPoint, " Get 方法，没有拦截到请求参数，请求将会被放行");
            }
        } else if (SpringServletContextUtil.isPostRequest(request)) {
//  如果是POST請求
            Object[] args = joinPoint.getArgs();
            if (args.length > 1) {
                AsConsoleConsoleUtil.error(joinPoint, "此注解 RequestBody 仅适配 Map 和 Object 对象, 当接收参数超过1个时，请使用Get方法。此次请求将会被直接放行");
                return joinPoint.proceed(joinPoint.getArgs());
            }
            if (args.length == 0) {
                AsConsoleConsoleUtil.error(joinPoint, "接收参数不能为空");
                return MsgResultVO.getInstanceError(MsgConstant.REQUEST_PARAMETER_ERROR);
            }
//          从接收封装的对象
            Map<String, Object> paramsBodyMap = new HashMap<>();
            Object paramsVO = args[0];
            if (ClassTypeUtil.isPackClass(paramsVO) || ClassTypeUtil.isBaseClass(paramsVO)) {
                AsConsoleConsoleUtil.error(joinPoint, "此注解只接收 Map 或 Object 对象");
            } else {
                if (paramsVO instanceof List<?>) {
                    Optional<?> any = ((List<?>) paramsVO).stream().filter(it -> checkListParams(it, notNull, notAllNull)).findAny();
                    if (any.isPresent()) {
                        return MsgResultVO.getInstanceError(MsgConstant.REQUEST_PARAMETER_ERROR);
                    } else {
                        return joinPoint.proceed(joinPoint.getArgs());
                    }
                }
//                使用Map接收参数
                if (paramsVO instanceof Map) {
                    paramsBodyMap = (Map<String, Object>) paramsVO;
                } else {
//                  使用对象接收参数
                    Field[] fields = ObjectUtil.getSupperClassProperties(paramsVO, new Field[0]);
                    for (Field field : fields) {
                        field.setAccessible(true);
                        paramsBodyMap.put(field.getName(), field.get(paramsVO));
                    }
                }
                for (String param : notNull) {
                    if (RegexUtil.isEmpty(paramsBodyMap.get(param))) {
                        paramsIsNull.add(param);
                        hasNull = true;
                    }
                }
                for (String param : notAllNull) {
                    if (!RegexUtil.isEmpty(paramsBodyMap.get(param))) {
                        allOrNull = false;
                        break;
                    }
                }
            }
        } else {
            AsConsoleConsoleUtil.error(joinPoint, "此注解仅适配 Get 和 Post 方法，此次请求将会被放行");
            return joinPoint.proceed(joinPoint.getArgs());
        }
        if (hasNull) {
            result.errorResult(MsgConstant.REQUEST_PARAMETER_ERROR.getCode(), backStr("请求参数 %s 不能为null", paramsIsNull));
            AsConsoleConsoleUtil.error(joinPoint, result.getResultMsg());
            return result;
        } else if (notAllNull.length > 0 && allOrNull) {
            result.errorResult(MsgConstant.REQUEST_PARAMETER_ERROR.getCode(), backStr("请求参数 %s 不能全部为null", Arrays.asList(notAllNull)));
            AsConsoleConsoleUtil.error(joinPoint, result.getResultMsg());
            return result;
        } else {
            AsConsoleConsoleUtil.info(joinPoint, "参数检查成功");
            return joinPoint.proceed(joinPoint.getArgs());
        }
    }


    /**
     * 替換參數, 將參數指定為指定格式
     * @param str 字符串
     * @param arr 集合
     * @return 字符串
     */
    private static String backStr(String str, List arr) {
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
    private static <T> boolean checkListParams(T paramsVO, String[] notNull, String[] notAllNull) {
        Map<String, Object> paramsBodyMap = new HashMap<>();
        boolean allOrNull = true;
        //                使用Map接收参数
        if (paramsVO instanceof Map) {
            paramsBodyMap = (Map<String, Object>) paramsVO;
        } else {
//                  使用对象接收参数
            Field[] fields = ObjectUtil.getSupperClassProperties(paramsVO, new Field[0]);
            for (Field field : fields) {
                field.setAccessible(true);
                try {
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
