package com.owl.shiro.controller;


import com.owl.comment.annotations.*;
import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.shiro.dto.ResetPasswordDTO;
import com.owl.shiro.model.OwlUser;
import com.owl.shiro.service.OwlAuthService;
import com.owl.shiro.service.OwlRoleService;
import com.owl.shiro.service.OwlUserService;
import com.owl.shiro.util.SigninerUtil;
import com.owl.shiro.vo.TestVO;
import com.owl.util.RegexUtil;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2018/03/24.
 */
@OwlLogInfo
@RestController
@RequestMapping(value = "auth", method = RequestMethod.POST, consumes = "application/json")
public class AuthController {

    private static final Logger logger = Logger.getLogger(AuthController.class.getName());
    @Resource
    private OwlAuthService owlAuthService;
    @Resource
    private OwlUserService owlUserService;
    @Resource
    private OwlRoleService owlRoleService;


    /**
     * 登陸
     * @param user (account password)
     * @return MsgResult
     */
    @RequestMapping("signin")
    @OwlCountTime
    @OwlCheckParams(notAllNull = {"account", "email", "mobile"}, notNull = {"password"})
    public MsgResultVO signin(@RequestBody OwlUser user) {
        return owlAuthService.signin(user);
    }

    /**
     * 用于刷新session
     * @return MsgResult
     */
    @RequestMapping("heartSkip")
    public MsgResultVO heartSkip() {
        MsgResultVO<OwlUser> result = new MsgResultVO<>();
        OwlUser user = (OwlUser) SecurityUtils.getSubject().getPrincipal();
        if (null == user) {
            logger.info("your are not login,please signin");
            result.errorResult(MsgConstant.REQUEST_NO_SIGNIN);
        } else {
            logger.info(user.getName() + " get heart skip.userId:" + user.getId());
            result.successResult(user);
        }
        return result;
    }

    /**
     * 未登錄
     * @return MsgResult
     */
    @RequestMapping("noSignin")
    public static MsgResultVO noSignin() {
        return MsgResultVO.getInstanceError(MsgConstant.REQUEST_NO_SIGNIN);
    }

    /**
     * 权限拒绝
     * @return MsgResult
     */
    @RequestMapping("permissionDefined")
    public static MsgResultVO permissionDefined() {
        return MsgResultVO.getInstanceError(MsgConstant.REQUEST_PERMISSION_DEFINED);
    }


    /**
     * 退出登錄
     * @return MsgResult
     */
    @OwlTry
    @RequestMapping("signout")
    public MsgResultVO signout() {
        logger.info(SigninerUtil.getSigninerName() + " signout");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return MsgResultVO.getInstanceSuccess();
    }

    /**
     * 注册
     * @return MsgResult
     */
    @RequestMapping("signup")
    @OwlCheckParams(notNull = {"password"}, notAllNull = {"mobile", "email"})
    public MsgResultVO signup(@RequestBody OwlUser user) {
        MsgResultVO result = new MsgResultVO();
        if (null != user.getEmail() && !RegexUtil.isEmail(user.getEmail())) {
            result.errorResult(MsgConstant.PARAM_EMAIL_ERROR);
        } else if (null != user.getMobile() && !RegexUtil.isMobile(user.getMobile())) {
            result.errorResult(MsgConstant.PARAM_MOBILE_ERROR);
        } else {
            result = owlUserService.create(user).aimMsg();
        }
        return result;
    }

    /**
     * 檢查用戶是否注冊
     * @param model 用戶對象
     * @return
     */
    @RequestMapping("isExist")
    @OwlCheckParams(notAllNull = {"mobile", "email", "account"})
    public MsgResultVO isExist(@RequestBody OwlUser model) {
        return owlUserService.isExist(model);
    }


    /**
     * 重置密碼接口
     * @param resetPasswordDTO
     * @return
     */
    @RequestMapping("resetPassword")
    @OwlCheckParams(notAllNull = {"mobile", "email", "account"}, notNull = {"oldPassword", "newPassword"})
    public MsgResultVO resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        return owlAuthService.resetPassword(resetPasswordDTO).aimMsg();
    }

}
