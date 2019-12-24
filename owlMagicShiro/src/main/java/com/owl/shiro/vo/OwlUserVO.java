package com.owl.shiro.vo;

import com.owl.shiro.model.OwlRole;
import com.owl.shiro.model.OwlUser;

import javax.management.relation.RoleList;
import java.util.List;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/5/7.
 */
public class OwlUserVO extends OwlUser {
    private List<OwlRole> roleList;

    private List<OwlPageVO> pageList;

    public List<OwlRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<OwlRole> roleList) {
        this.roleList = roleList;
    }

    public List<OwlPageVO> getPageList() {
        return pageList;
    }

    public void setPageList(List<OwlPageVO> pageList) {
        this.pageList = pageList;
    }
}
