package com.owl.shiro.service;

import com.owl.mvc.dto.RelationDTO;
import com.owl.mvc.service.RelationBaseServiceAb;
import com.owl.mvc.so.ModelSO;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.shiro.dao.OwlRoleMenuDao;
import com.owl.shiro.model.OwlRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/4/30.
 */
@Service
public class ReRoleMenuService extends RelationBaseServiceAb<OwlRoleMenuDao,OwlRoleMenu> {
    @Resource
    private OwlRoleMenuDao owlRoleMenuDao;

    @Override
    public MsgResultVO update(RelationDTO relationDTO) {
        OwlRoleMenu owlRoleMenu = new OwlRoleMenu();
        owlRoleMenu.setRoleId(relationDTO.getId());
        owlRoleMenuDao.delete(ModelSO.getInstance(owlRoleMenu));
        owlRoleMenuDao.insertRelation(relationDTO);
        return MsgResultVO.getInstanceSuccess();
    }
}
