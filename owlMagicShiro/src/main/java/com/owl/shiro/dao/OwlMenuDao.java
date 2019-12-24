package com.owl.shiro.dao;

import com.owl.mvc.dao.CellBaseDao;
import com.owl.mvc.so.IdListSO;
import com.owl.shiro.model.OwlMenu;
import com.owl.shiro.model.OwlPage;
import com.owl.shiro.model.OwlRolePage;

import java.util.Set;

public interface OwlMenuDao extends CellBaseDao<OwlMenu> {
    Set<OwlMenu> menuListByRole(IdListSO idListSO);

    Set<OwlMenu> menuListByPage(OwlPage owlPage);

    Set<OwlMenu> menuListByPageRole(OwlRolePage owlRolePage);
}