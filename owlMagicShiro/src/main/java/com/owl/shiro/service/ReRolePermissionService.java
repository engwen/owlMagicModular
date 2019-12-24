package com.owl.shiro.service;

import com.owl.mvc.dto.RelationDTO;
import com.owl.mvc.service.RelationBaseServiceAb;
import com.owl.mvc.so.ModelSO;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.shiro.dao.OwlRolePermissionDao;
import com.owl.shiro.model.OwlRolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/4/30.
 */
@Service
public class ReRolePermissionService extends RelationBaseServiceAb<OwlRolePermissionDao, OwlRolePermission> {
    @Resource
    private OwlRolePermissionDao owlRolePermissionDao;
    @Resource
    private FilterChainDefinitionsService filterChainDefinitionsService;

    @Override
    public MsgResultVO insert(OwlRolePermission model) {
        MsgResultVO resultVO = super.insert(model);
        filterChainDefinitionsService.reloadFilterChains();
        return resultVO;
    }

    @Override
    public MsgResultVO insertRelation(RelationDTO relationDTO) {
        MsgResultVO resultVO = super.insertRelation(relationDTO);
        filterChainDefinitionsService.reloadFilterChains();
        return resultVO;
    }

    @Override
    public MsgResultVO insertList(List<OwlRolePermission> modelList) {
        MsgResultVO resultVO = super.insertList(modelList);
        filterChainDefinitionsService.reloadFilterChains();
        return resultVO;
    }

    @Override
    public MsgResultVO delete(OwlRolePermission model) {
        MsgResultVO resultVO = super.delete(model);
        filterChainDefinitionsService.reloadFilterChains();
        return resultVO;
    }

    @Override
    public MsgResultVO deleteRelation(RelationDTO relationDTO) {
        MsgResultVO resultVO = super.deleteRelation(relationDTO);
        filterChainDefinitionsService.reloadFilterChains();
        return resultVO;
    }

    @Override
    public MsgResultVO deleteList(List<OwlRolePermission> modelList) {
        MsgResultVO resultVO = super.deleteList(modelList);
        filterChainDefinitionsService.reloadFilterChains();
        return resultVO;
    }

    @Override
    public MsgResultVO update(RelationDTO relationDTO) {
        OwlRolePermission owlRolePermission = new OwlRolePermission();
        owlRolePermission.setRoleId(relationDTO.getId());
        owlRolePermissionDao.delete(ModelSO.getInstance(owlRolePermission));
        owlRolePermissionDao.insertRelation(relationDTO);
        filterChainDefinitionsService.reloadFilterChains();
        return MsgResultVO.getInstanceSuccess();
    }
}
