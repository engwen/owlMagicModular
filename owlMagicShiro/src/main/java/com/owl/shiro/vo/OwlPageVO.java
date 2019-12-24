package com.owl.shiro.vo;

import com.owl.shiro.model.OwlMenu;
import com.owl.shiro.model.OwlPage;

import java.util.List;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/5/7.
 */
public class OwlPageVO extends OwlPage {
    private List<OwlMenu> menuList;

    public List<OwlMenu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<OwlMenu> menuList) {
        this.menuList = menuList;
    }
}
