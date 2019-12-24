package com.owl.shiro.dao;

import com.owl.mvc.dao.CellBaseDao;
import com.owl.mvc.dto.BanListDTO;
import com.owl.mvc.dto.DeleteDTO;
import com.owl.mvc.so.ModelListSO;
import com.owl.mvc.so.SelectLikeSO;
import com.owl.shiro.model.OwlRole;
import com.owl.shiro.model.OwlUser;

import java.util.List;

public interface OwlUserDao extends CellBaseDao<OwlUser> {
    List<OwlRole> selectRoleByUserID(Long id);
}