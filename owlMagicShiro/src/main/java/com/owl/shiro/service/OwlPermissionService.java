package com.owl.shiro.service;

import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.service.CellBaseServiceAb;
import com.owl.mvc.so.ModelSO;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.shiro.dao.OwlPermissionDao;
import com.owl.shiro.dao.OwlRolePermissionDao;
import com.owl.shiro.model.OwlPermission;
import com.owl.shiro.model.OwlRolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/03/27.
 */
@Service
public class OwlPermissionService extends CellBaseServiceAb<OwlPermissionDao, OwlPermission> {

    @Resource
    private OwlPermissionDao owlPermissionDao;
    @Resource
    private OwlRolePermissionDao owlRolePermissionDao;

    @Override
    @Transactional
    public MsgResultVO delete(OwlPermission model) {
        MsgResultVO<OwlPermission> result = details(model);
        if (result.getResult()) {
            owlPermissionDao.deleteBySelective(result.getResultData());
            OwlRolePermission rolePermission = new OwlRolePermission();
            rolePermission.setPermissionId(result.getResultData().getId());
            owlRolePermissionDao.delete(ModelSO.getInstance(rolePermission));
            result.successResult();
        } else {
            result.errorResult(MsgConstant.REQUEST_NOT_EXITS);
        }
        return result;
    }
}
