package com.owl.shiro.service;

import com.owl.mvc.dto.RelationDTO;
import com.owl.mvc.service.RelationBaseServiceAb;
import com.owl.mvc.so.ModelSO;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.shiro.dao.OwlUserRoleDao;
import com.owl.shiro.model.OwlUserRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/4/30.
 */
@Service
public class ReUserRoleService extends RelationBaseServiceAb<OwlUserRoleDao, OwlUserRole> {
    @Resource
    private OwlUserRoleDao owlUserRoleDao;

    @Override
    public MsgResultVO update(RelationDTO relationDTO) {
        OwlUserRole userRole = new OwlUserRole();
        userRole.setUserId(relationDTO.getId());
        owlUserRoleDao.delete(ModelSO.getInstance(userRole));
        owlUserRoleDao.insertRelation(relationDTO);
        return MsgResultVO.getInstanceSuccess();
    }
}
