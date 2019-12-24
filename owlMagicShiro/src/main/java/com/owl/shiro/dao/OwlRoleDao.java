package com.owl.shiro.dao;

import com.owl.mvc.dao.CellBaseDao;
import com.owl.mvc.dto.BanListDTO;
import com.owl.mvc.dto.DeleteDTO;
import com.owl.mvc.so.ModelListSO;
import com.owl.mvc.so.SelectLikeSO;
import com.owl.shiro.model.OwlRole;

import java.util.List;

public interface OwlRoleDao extends CellBaseDao<OwlRole> {
    List<OwlRole> selectRoleByPermissionId(Long id);
}