package com.owl.shiro.model;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * time 2018/03/26.
 */
public class OwlRolePermission {
    //角色ID
    private Long roleId;
    //权限ID
    private Long permissionId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }
}
