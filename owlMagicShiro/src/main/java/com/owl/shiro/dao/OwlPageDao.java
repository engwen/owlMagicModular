package com.owl.shiro.dao;

import com.owl.mvc.dao.CellBaseDao;
import com.owl.mvc.so.IdListSO;
import com.owl.shiro.model.OwlPage;

import java.util.Set;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/4/29.
 */
public interface OwlPageDao extends CellBaseDao<OwlPage> {

    Set<OwlPage> pageListByRole(IdListSO idListSO);

}
