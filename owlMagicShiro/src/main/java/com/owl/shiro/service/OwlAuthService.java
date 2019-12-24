package com.owl.shiro.service;

import com.owl.util.MD5Util;
import com.owl.util.ObjectUtil;
import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.shiro.dao.OwlUserDao;
import com.owl.shiro.dto.ResetPasswordDTO;
import com.owl.shiro.model.OwlUser;
import com.owl.shiro.realm.OwlShiroToken;
import com.owl.shiro.util.SigninerUtil;
import com.owl.shiro.vo.OwlUserVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 登录以及权限验证
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/03/26.
 */
@Service
public class OwlAuthService {
    @Resource
    private OwlUserDao owlUserDao;
    @Resource
    private OwlUserService owlUserService;
    @Resource
    private OwlRoleService owlRoleService;


    /**
     * 登录
     * @param user
     * @return
     */
    public MsgResultVO signin(OwlUser user) {
        MsgResultVO<OwlUser> result = new MsgResultVO<>();
        OwlShiroToken owlShiroToken;
        if (null != user.getAccount()) {
            owlShiroToken = new OwlShiroToken(user.getAccount(), user.getPassword(), OwlShiroToken.TokenType.ACCOUNT);
        } else if (null != user.getEmail()) {
            owlShiroToken = new OwlShiroToken(user.getEmail(), user.getPassword(), OwlShiroToken.TokenType.EMAIL);
        } else {
            owlShiroToken = new OwlShiroToken(user.getMobile(), user.getPassword(), OwlShiroToken.TokenType.MOBILE);
        }
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(owlShiroToken);
            OwlUserVO userVO = new OwlUserVO();
            ObjectUtil.setThisObjToAnotherObj(SigninerUtil.getSigniner(), userVO);
            List<Long> roleIdList = owlUserService.roleIdListBySigniner();
            userVO.setPageList(owlRoleService.pageMenuListByRole(roleIdList));
            result.successResult(userVO);
        } catch (Exception e) {
            e.printStackTrace();
            result.errorResult(MsgConstant.REQUEST_ACCOUNT_OR_PASSWORD_ERROR);
        }
        return result;
    }

    /**
     * 檢查用戶的賬號以及密碼
     * @param model 用户信息
     * @return
     */
    public MsgResultVO checkPassword(OwlUser model) {
        MsgResultVO<OwlUser> result = new MsgResultVO<>();
        OwlUser signer = owlUserService.details(model).getResultData();
        if (null != signer && signer.getPassword().equals(MD5Util.getSecretPsw(model.getPassword()))) {
            model = signer;
            model.setPassword(null);
            result.successResult(model);
        } else {
            result.errorResult(MsgConstant.REQUEST_ACCOUNT_OR_PASSWORD_ERROR);
        }
        return result;
    }

    /**
     * 重置密碼
     * @param resetPasswordDTO
     * @return MsgResult
     */
    @Transactional
    public MsgResultVO resetPassword(ResetPasswordDTO resetPasswordDTO) {
        MsgResultVO result = new MsgResultVO();
        OwlUser model = new OwlUser();
        model.setAccount(resetPasswordDTO.getAccount());
        model.setMobile(resetPasswordDTO.getMobile());
        model.setEmail(resetPasswordDTO.getEmail());
        OwlUser user = owlUserService.details(model).getResultData();
        if (null == user) {
            result.errorResult(MsgConstant.REQUEST_ACCOUNT_OR_PASSWORD_ERROR);
        } else {
            if (!MD5Util.getSecretPsw(resetPasswordDTO.getOldPassword()).equals(user.getPassword())) {
                result.errorResult(MsgConstant.REQUEST_ACCOUNT_OR_PASSWORD_ERROR);
            } else {
                user.setPassword(MD5Util.getSecretPsw(resetPasswordDTO.getNewPassword()));
                owlUserDao.updateBySelective(user);
                result.successResult();
            }
        }
        return result;
    }
}
