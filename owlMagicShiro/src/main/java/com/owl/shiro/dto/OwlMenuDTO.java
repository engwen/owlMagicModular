package com.owl.shiro.dto;

import com.owl.shiro.model.OwlMenu;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/5/17.
 */
public class OwlMenuDTO extends OwlMenu {
    private Long pageId;
    private Long roleId;

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
