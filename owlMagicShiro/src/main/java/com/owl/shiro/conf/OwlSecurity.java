package com.owl.shiro.conf;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录校验,并提供获取对应的token获取BMUserDetailsVO
 * @author engwen
 * email xiachanzou@outlook.com
 * 2017/4/15.
 */
public class OwlSecurity extends HandlerInterceptorAdapter {
    private static Logger logger = Logger.getLogger(OwlSecurity.class.getName());
//
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        return true;
//    }
//
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//    }
//
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//    }
//
//    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Headers", "DNT,X-Mx-ReqToken,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Authorization,SessionToken");
        response.setHeader("Access-Control-Expose-Headers", request.getHeader("Origin"));
        return super.preHandle(request, response, handler);
//        if (null == response.getHeader("Access-Control-Allow-Origin") || !response.getHeader("Access-Control-Allow-Origin").equals("*")) {
//            response.addHeader("Access-Control-Allow-Origin", "*");
//        }
//        return super.preHandle(request, response, handler);
    }
}
