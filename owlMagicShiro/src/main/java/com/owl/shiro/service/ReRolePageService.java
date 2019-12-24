package com.owl.shiro.service;

import com.owl.mvc.dao.RelationBaseDao;
import com.owl.mvc.dto.RelationDTO;
import com.owl.mvc.service.RelationBaseServiceAb;
import com.owl.mvc.so.ModelSO;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.shiro.dao.OwlRolePageDao;
import com.owl.shiro.model.OwlRolePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/4/30.
 */
@Service
public class ReRolePageService extends RelationBaseServiceAb<OwlRolePageDao, OwlRolePage> {
    @Resource
    private OwlRolePageDao owlRolePageDao;

    @Override
    public MsgResultVO update(RelationDTO relationDTO) {
        OwlRolePage rolePage = new OwlRolePage();
        rolePage.setRoleId(relationDTO.getId());
        owlRolePageDao.delete(ModelSO.getInstance(rolePage));
        owlRolePageDao.insertRelation(relationDTO);
        return MsgResultVO.getInstanceSuccess();
    }
}
