package com.owl.shiro.dao;

import com.owl.mvc.dao.CellBaseDao;
import com.owl.mvc.so.IdListSO;
import com.owl.shiro.model.OwlPermission;

import java.util.Set;

public interface OwlPermissionDao extends CellBaseDao<OwlPermission> {
    Set<OwlPermission> permissionListByRole(IdListSO idListSO);
}