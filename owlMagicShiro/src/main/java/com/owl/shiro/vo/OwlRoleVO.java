package com.owl.shiro.vo;

import com.owl.shiro.model.OwlMenu;
import com.owl.shiro.model.OwlPage;
import com.owl.shiro.model.OwlPermission;
import com.owl.shiro.model.OwlRole;

import java.util.List;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/5/16.
 */
public class OwlRoleVO extends OwlRole {
    //具备的权限
    private List<OwlPermission> permissionList;

    private List<OwlPage> pageList;

    private List<OwlMenu> menuList;

    private List<Long> idList;

    private List<Long> othIdList;

    public List<OwlPermission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<OwlPermission> permissionList) {
        this.permissionList = permissionList;
    }

    public List<OwlPage> getPageList() {
        return pageList;
    }

    public void setPageList(List<OwlPage> pageList) {
        this.pageList = pageList;
    }

    public List<OwlMenu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<OwlMenu> menuList) {
        this.menuList = menuList;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

    public List<Long> getOthIdList() {
        return othIdList;
    }

    public void setOthIdList(List<Long> othIdList) {
        this.othIdList = othIdList;
    }
}
