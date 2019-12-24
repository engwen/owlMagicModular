package com.owl.shiro.service;


import com.owl.common.CommentEM;
import com.owl.util.ObjectUtil;
import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.service.CellBaseServiceAb;
import com.owl.mvc.so.IdListSO;
import com.owl.mvc.so.ModelSO;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.shiro.dao.*;
import com.owl.shiro.model.*;
import com.owl.shiro.vo.OwlPageVO;
import com.owl.shiro.vo.OwlRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/03/24.
 */
@Service
public class OwlRoleService extends CellBaseServiceAb<OwlRoleDao, OwlRole> {

    @Resource
    private OwlUserRoleDao owlUserRoleDao;
    @Resource
    private OwlRoleDao owlRoleDao;
    @Resource
    private OwlPageDao owlPageDao;
    @Resource
    private OwlMenuDao owlMenuDao;
    @Resource
    private OwlPermissionDao owlPermissionDao;
    @Resource
    private OwlRolePermissionDao owlRolePermissionDao;
    @Resource
    private OwlRoleMenuDao owlRoleMenuDao;
    @Resource
    private OwlRolePageDao owlRolePageDao;

    @Override
    @Transactional
    public MsgResultVO delete(OwlRole owlRole) {
        MsgResultVO<OwlRole> result = details(owlRole);
        if (result.getResult()) {
            if (!result.getResultData().getRole().equals(CommentEM.Role.ADMIN.role)) {
                owlRoleDao.deleteBySelective(owlRole);
                OwlUserRole userRole = new OwlUserRole();
                OwlRoleMenu roleMenu = new OwlRoleMenu();
                OwlRolePage rolePage = new OwlRolePage();
                OwlRolePermission rolePermission = new OwlRolePermission();
                userRole.setRoleId(owlRole.getId());
                roleMenu.setRoleId(owlRole.getId());
                rolePage.setRoleId(owlRole.getId());
                rolePermission.setRoleId(owlRole.getId());
                owlUserRoleDao.delete(ModelSO.getInstance(userRole));
                owlRoleMenuDao.delete(ModelSO.getInstance(roleMenu));
                owlRolePageDao.delete(ModelSO.getInstance(rolePage));
                owlRolePermissionDao.delete(ModelSO.getInstance(rolePermission));
                result.successResult();
            } else {
                result.errorResult(MsgConstant.REQUEST_CANT_UPDATE_ADMIN);
            }
        } else {
            result.errorResult(MsgConstant.REQUEST_NOT_EXITS);
        }
        return result;
    }

    public MsgResultVO<OwlRoleVO> rolePermissionList(OwlRolePermission rolePermission) {
        OwlRoleVO owlRoleVO = new OwlRoleVO();
        owlRoleVO.setId(rolePermission.getRoleId());
        List<Long> idList = new ArrayList<>();
        idList.add(rolePermission.getRoleId());
        owlRoleVO.setPermissionList(owlPermissionDao.selectBySelective(null));
        List<Long> hasIdList = new ArrayList<>();
        owlPermissionDao.permissionListByRole(IdListSO.getInstance(idList)).forEach(it -> hasIdList.add(it.getId()));
        owlRoleVO.setIdList(hasIdList);
        return MsgResultVO.getInstanceSuccess(owlRoleVO);
    }

    public MsgResultVO<OwlRoleVO> rolePageList(OwlRolePage rolePage) {
        OwlRoleVO owlRoleVO = new OwlRoleVO();
        owlRoleVO.setId(rolePage.getRoleId());
        List<Long> idList = new ArrayList<>();
        idList.add(rolePage.getRoleId());
        List<OwlPage> pageList = owlPageDao.selectBySelective(null);
        List<OwlPage> pageVOList = new ArrayList<>();
        pageList.forEach(it -> {
            OwlPageVO temp = ObjectUtil.setThisObjToAnotherObj(it, new OwlPageVO());
            temp.setMenuList(new ArrayList<>(owlMenuDao.menuListByPage(it)));
            pageVOList.add(temp);
        });
        owlRoleVO.setPageList(pageVOList);
        List<Long> hasIdList = new ArrayList<>();
        owlPageDao.pageListByRole(IdListSO.getInstance(idList)).forEach(it -> hasIdList.add(it.getId()));
        owlRoleVO.setIdList(hasIdList);
        List<Long> hasMenuIdList = new ArrayList<>();
        owlMenuDao.menuListByRole(IdListSO.getInstance(idList)).forEach(it -> hasMenuIdList.add(it.getId()));
        owlRoleVO.setOthIdList(hasMenuIdList);
        return MsgResultVO.getInstanceSuccess(owlRoleVO);
    }

    public MsgResultVO<OwlRoleVO> roleMenuList(OwlRoleMenu roleMenu) {
        OwlRoleVO owlRoleVO = new OwlRoleVO();
        owlRoleVO.setId(roleMenu.getRoleId());
        List<Long> idList = new ArrayList<>();
        idList.add(roleMenu.getRoleId());
        owlRoleVO.setMenuList(owlMenuDao.selectBySelective(null));
        List<Long> hasIdList = new ArrayList<>();
        owlMenuDao.menuListByRole(IdListSO.getInstance(idList)).forEach(it -> hasIdList.add(it.getId()));
        owlRoleVO.setIdList(hasIdList);
        return MsgResultVO.getInstanceSuccess(owlRoleVO);
    }

    public List<OwlRole> findRoleByPermissionId(Long permissionID) {
        return owlRoleDao.selectRoleByPermissionId(permissionID);
    }

    public List<OwlPermission> permissionListByRole(List<Long> roleIDs) {
        return new ArrayList<>(owlPermissionDao.permissionListByRole(IdListSO.getInstance(roleIDs)));
    }


    public List<OwlMenu> menuListByRole(List<Long> roleIDs) {
        return new ArrayList<>(owlMenuDao.menuListByRole(IdListSO.getInstance(roleIDs)));
    }


    public List<OwlPageVO> pageListByRole(List<Long> idList) {
        List<OwlPageVO> pageVOList = new ArrayList<>();
        List<OwlPage> pageList = new ArrayList<>(owlPageDao.pageListByRole(IdListSO.getInstance(idList)));
        pageList.forEach(it -> pageVOList.add(ObjectUtil.setThisObjToAnotherObj(it, new OwlPageVO())));
        return pageVOList;
    }

    public List<OwlMenu> menuListByPageRole(OwlRolePage model) {
        return new ArrayList<>(owlMenuDao.menuListByPageRole(model));
    }

    public List<OwlPageVO> pageMenuListByRole(List<Long> idList) {
        List<OwlPageVO> pageVOList = pageListByRole(idList);
        pageVOList.forEach(it -> {
            Set<OwlMenu> temp = new HashSet<>();
            idList.forEach(roleId -> {
                OwlRolePage owlRolePage = new OwlRolePage();
                owlRolePage.setRoleId(roleId);
                owlRolePage.setPageId(it.getId());
                temp.addAll(owlMenuDao.menuListByPageRole(owlRolePage));
            });
            it.setMenuList(new ArrayList<>(temp));
        });
        return pageVOList;
    }
}
