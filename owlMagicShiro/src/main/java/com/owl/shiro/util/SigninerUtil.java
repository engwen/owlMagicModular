package com.owl.shiro.util;

import com.owl.shiro.model.OwlUser;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;

/**
 * 关于登录用户
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/05/14.
 */
public class SigninerUtil {
    private static Logger logger = Logger.getLogger(SigninerUtil.class.getName());

    /**
     * 獲取登陸用戶
     *
     * @return
     */
    public static OwlUser getSigniner() {
        OwlUser signiner = null;
        try {
            signiner = (OwlUser) SecurityUtils.getSubject().getPrincipal();
        } catch (Exception e) {
            logger.error("get signiner is error!");
        } finally {
            if (null == signiner) {
                signiner = new OwlUser();
            }
        }
        return signiner;
    }

    /**
     * 獲取登陸用戶信息
     *
     * @return
     */
    public static String getSigninerName() {
        return getSigniner().getName();
    }
}
