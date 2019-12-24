package com.owl.shiro.realm;

import com.owl.mvc.vo.MsgResultVO;
import com.owl.shiro.model.OwlPermission;
import com.owl.shiro.model.OwlRole;
import com.owl.shiro.model.OwlUser;
import com.owl.shiro.service.OwlAuthService;
import com.owl.shiro.service.OwlRoleService;
import com.owl.shiro.service.OwlUserService;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/03/26.
 */
public class OwlShiroRealm extends AuthorizingRealm {
    private Logger logger = Logger.getLogger(OwlShiroRealm.class.getName());

    @Resource
    private OwlAuthService owlAuthService;
    @Resource
    private OwlUserService owlUserService;
    @Resource
    private OwlRoleService owlRoleService;

    /**
     * 授予权限
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        OwlUser user = (OwlUser) (principalCollection.getPrimaryPrincipal());
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<OwlRole> roleList = owlUserService.roleListByUserID(user.getId());
        List<Long> roleIDList = new ArrayList<>();
        Set<String> roleStrList = new HashSet<>();
        roleList.forEach(role -> {
            roleStrList.add(role.getRole());
            roleIDList.add(role.getId());
        });
        authorizationInfo.setRoles(roleStrList);
        List<OwlPermission> permissionList;
        if (roleIDList.size() == 0) {
            permissionList = new ArrayList<>();
        } else {
            permissionList = owlRoleService.permissionListByRole(roleIDList);
        }
        Set<String> permissionStrList = new HashSet<>();
        permissionList.forEach(permission -> permissionStrList.add(permission.getPermissionUrl()));
        authorizationInfo.setStringPermissions(permissionStrList);
        return authorizationInfo;
    }

    /**
     * 校验登录情况
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        OwlShiroToken owlShiroToken = (OwlShiroToken) authenticationToken;
        OwlUser model = new OwlUser();
        switch (owlShiroToken.getTokenType()) {
            case ACCOUNT:
                model.setAccount(owlShiroToken.getUsername());
                break;
            case EMAIL:
                model.setEmail(owlShiroToken.getUsername());
                break;
            case MOBILE:
                model.setMobile(owlShiroToken.getUsername());
                break;
        }
        model.setPassword(owlShiroToken.getPasswordStr());
        MsgResultVO result = owlAuthService.checkPassword(model);
        OwlUser user = (OwlUser) result.getResultData();
        if (!result.getResult()) {
            logger.info("帐号或密码不正确！");
            throw new AccountException();
        } else if (user.getIsBan()) {
            logger.info("账号已被禁用！");
            throw new AccountException();
        } else if (!user.getStatus()) {
            logger.info("账号不可登录！");
            throw new AccountException();
        } else {
            logger.info("登录成功！");
        }
        return new SimpleAuthenticationInfo(user, owlShiroToken.getPasswordStr(), this.getName());
    }
}
