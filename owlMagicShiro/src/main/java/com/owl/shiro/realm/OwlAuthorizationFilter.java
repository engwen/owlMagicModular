package com.owl.shiro.realm;

import com.owl.shiro.controller.AuthController;
import com.owl.shiro.util.SigninerUtil;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.json.JSONObject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * AuthorizationFilter抽象类事项了javax.servlet.Filter接口，它是个过滤器。
 * 重写 shiro 过滤器，包含所有权限变更————————》包含一个权限即可
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/03/28.
 */
public class OwlAuthorizationFilter extends AuthorizationFilter {
    private static Logger logger = Logger.getLogger(OwlAuthorizationFilter.class.getName());
    private static String OPTIONS = "OPTIONS";

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) throws Exception {
        logger.debug("check role");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //放过OPTIONS请求
        if (OwlAuthorizationFilter.OPTIONS.equals(request.getMethod())) {
            return true;
        }

        Subject subject = getSubject(servletRequest, servletResponse);
        String[] rolesArray = (String[]) mappedValue;
        //没有角色限制，有权限访问
        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }
        return Arrays.stream(rolesArray).anyMatch(subject::hasRole);
//        for (String aRolesArray : rolesArray) {
//            //若当前用户是rolesArray中的任何一个，则有权限访问
//            if (subject.hasRole(aRolesArray)) {
//                return true;
//            }
//        }
//        return false;
    }

    //登录超时以及未登录用户将会被拦截
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.addHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        JSONObject jsonObject;
        if (null != SecurityUtils.getSubject().getPrincipal()) {
            jsonObject = new JSONObject(AuthController.permissionDefined());
        } else {
            jsonObject = new JSONObject(AuthController.noSignin());
        }
        httpServletResponse.getWriter().write(jsonObject.toString());
        return false;
    }
}
