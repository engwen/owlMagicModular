package com.owl.shiro.model;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/03/26.
 */
public class OwlUserRole {

    /** 用户ID {@link OwlUser.id}*/
    private Long userId;
    /** 角色ID {@link OwlRole.id}*/
    private Long roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
