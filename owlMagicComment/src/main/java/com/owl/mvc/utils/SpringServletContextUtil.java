package com.owl.mvc.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 获取 request 对象
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/8/22.
 */
public class SpringServletContextUtil {
    public static final String GET_METHOD = "GET";
    public static final String POST_METHOD = "POST";

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpServletResponse gerResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public static boolean isGetRequest() {
        return GET_METHOD.equals(SpringServletContextUtil.getRequest().getMethod());
    }

    public static boolean isGetRequest(HttpServletRequest request) {
        return GET_METHOD.equals(request.getMethod());
    }

    public static boolean isPostRequest() {
        return POST_METHOD.equals(SpringServletContextUtil.getRequest().getMethod());
    }

    public static boolean isPostRequest(HttpServletRequest request) {
        return GET_METHOD.equals(request.getMethod());
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static String getHeader(String key) {
        return getRequest().getHeader(key);
    }

}
