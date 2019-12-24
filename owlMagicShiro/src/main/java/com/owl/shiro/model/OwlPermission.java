package com.owl.shiro.model;

/**
 * 权限基础类
 * @author engwen
 * email xiachanzou@outlook.com
 * time 2018/03/26.
 */
public class OwlPermission {
    //权限ID
    private Long id;
    //权限访问地址----需要自动追加
    private String permissionUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermissionUrl() {
        return permissionUrl;
    }

    public void setPermissionUrl(String permissionUrl) {
        this.permissionUrl = permissionUrl;
    }


    public OwlPermission() {
    }

    public OwlPermission(String permissionUrl) {
        this.permissionUrl = permissionUrl;
    }

    public OwlPermission(Long id, String permissionUrl) {
        this.id = id;
        this.permissionUrl = permissionUrl;
    }
}
