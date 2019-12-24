package com.owl.shiro.service;

import com.owl.common.CommentEM;
import com.owl.mvc.dto.BanDTO;
import com.owl.mvc.dto.BanListDTO;
import com.owl.mvc.dto.PageDTO;
import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.service.CellBaseServiceAb;
import com.owl.mvc.so.ModelSO;
import com.owl.mvc.so.SelectLikeSO;
import com.owl.mvc.utils.CellBaseServiceUtil;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.mvc.vo.PageVO;
import com.owl.shiro.dao.OwlRoleMenuDao;
import com.owl.shiro.dao.OwlUserDao;
import com.owl.shiro.dao.OwlUserRoleDao;
import com.owl.shiro.model.OwlRole;
import com.owl.shiro.model.OwlUser;
import com.owl.shiro.model.OwlUserRole;
import com.owl.shiro.util.SigninerUtil;
import com.owl.shiro.vo.OwlUserVO;
import com.owl.util.MD5Util;
import com.owl.util.ObjectUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 账号处理
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/03/24.
 */
@Service
public class OwlUserService extends CellBaseServiceAb<OwlUserDao,OwlUser> {
    @Resource
    private OwlUserDao owlUserDao;
    @Resource
    private OwlUserRoleDao owlUserRoleDao;
    @Resource
    private OwlRoleMenuDao owlRoleMenuDao;

    /**
     * 創建指定的用戶
     * @param user
     * @return
     */
    @Override
    @Transactional
    public MsgResultVO<OwlUser> create(OwlUser user) {
        MsgResultVO<OwlUser> result = new MsgResultVO<>();
        user.setStatus(true);
        user.setPassword(MD5Util.getSecretPsw(user.getPassword()));
        OwlUser temp = ObjectUtil.setThisObjToAnotherObj(user, new OwlUser());
        temp.setName(null);
        List<OwlUser> owlUserList = owlUserDao.selectByExact(SelectLikeSO.getInstance(temp));
        if (owlUserList.size() > 0) {
            result.errorResult(MsgConstant.REQUEST_IS_EXITS);
        } else {
            owlUserDao.insertSelective(user);
            user.setPassword(null);
            result.successResult(user);
        }
        return result;
    }

    @Override
    @Transactional
    public MsgResultVO delete(OwlUser model) {
        MsgResultVO result = new MsgResultVO();
        MsgResultVO<OwlUser> userDetails = details(model);
        if (userDetails.getResult()) {
            model = userDetails.getResultData();
            if (model.getAccount().equals(CommentEM.Role.ADMIN.name)) {
                result.errorResult(MsgConstant.REQUEST_CANT_UPDATE_ADMIN);
            } else {
                OwlUserRole userRole = new OwlUserRole();
                userRole.setUserId(model.getId());
                owlUserRoleDao.delete(ModelSO.getInstance(userRole));
                result = CellBaseServiceUtil.delete(owlUserDao, model);
            }
        } else {
            result.errorResult(MsgConstant.REQUEST_NOT_EXITS);
        }
        return result;
    }

    /**
     * 禁用或者启用指定的用戶
     * @param banDTO
     * @return
     */
    @Override
    @Transactional
    public MsgResultVO banOrLeave(BanDTO banDTO) {
        OwlUser user = new OwlUser();
        user.setId(banDTO.getId());
        MsgResultVO<OwlUser> result = details(user);
        if (result.getResult()) {
            user = result.getResultData();
            if (user.getAccount().equals("admin")) {
                result.errorResult(MsgConstant.REQUEST_CANT_UPDATE_ADMIN);
            } else {
                user.setIsBan(null != banDTO.getIsBan() ? banDTO.getIsBan() : !user.getStatus());
                owlUserDao.updateBySelective(user);
                result.successResult();
            }
        } else {
            result.errorResult(MsgConstant.REQUEST_NOT_EXITS);
        }
        return result;
    }

    /**
     * 移除指定的用戶集合
     * @return
     */
    @Override
    @Transactional
    public MsgResultVO banOrLeaveList(BanListDTO banListDTO) {
        MsgResultVO result = new MsgResultVO();
        OwlUser model = new OwlUser();
        model.setAccount("admin");
        OwlUser user = details(model).getResultData();
        if (banListDTO.getIdList().contains(user.getId())) {
            result.errorResult(MsgConstant.REQUEST_CANT_UPDATE_ADMIN);
        } else {
            owlUserDao.banOrLeave(banListDTO);
            result.successResult();
        }
        return result;
    }

    /**
     * 更新
     * @param model
     * @return
     */
    @Override
    @Transactional
    public MsgResultVO<OwlUser> update(OwlUser model) {
        MsgResultVO<OwlUser> result = new MsgResultVO<>();
        if (null != model.getStatus() && !model.getStatus()) {
            result.errorResult(MsgConstant.REQUEST_CANT_UPDATE_STATUS);
        } else {
            OwlUser user = details(model).getResultData();
            if (null != user && user.getStatus()) {
                model.setStatus(null);
                if (null != model.getPassword() && SecurityUtils.getSubject().hasRole("admin")) {
                    model.setPassword(MD5Util.getSecretPsw(model.getPassword()));
                } else {
                    model.setPassword(null);
                }
                OwlUser temp = ObjectUtil.setThisObjToAnotherObj(model, new OwlUser());
                temp.setId(null);
                temp.setName(null);
                List<OwlUser> owlUserList = owlUserDao.selectByExact(SelectLikeSO.getInstance(temp));
                if (owlUserList.size() == 1 && owlUserList.get(0).getId().equals(user.getId())) {
                    owlUserDao.updateBySelective(model);
                    result.successResult(model);
                } else if (owlUserList.size() > 1) {
                    result.errorResult(MsgConstant.REQUEST_IS_EXITS);
                } else {
                    result.errorResult(MsgConstant.REQUEST_NOT_EXITS);
                }
            } else {
                result.errorResult(MsgConstant.REQUEST_NOT_EXITS);
            }
        }
        return result;
    }

    @Override
    public MsgResultVO<PageVO<OwlUser>> list(PageDTO<OwlUser> pageDTO) {
        MsgResultVO<PageVO<OwlUser>> pageVOMsgResultVO = super.list(pageDTO);
        List<OwlUser> temp = new ArrayList<>();
        pageVOMsgResultVO.getResultData().getObjectList().forEach(it -> {
            OwlUserVO userVO = ObjectUtil.setThisObjToAnotherObj(it, new OwlUserVO());
            userVO.setRoleList(roleListByUserID(it.getId()));
            temp.add(userVO);
        });
        pageVOMsgResultVO.getResultData().setObjectList(temp);
        return pageVOMsgResultVO;
    }

    /*---------------------------------用戶角色關係----------------------------------------*/

    /**
     * 获取指定用户的角色
     * @param userID 用户ID
     * @return
     */
    public List<OwlRole> roleListByUserID(Long userID) {
        return owlUserDao.selectRoleByUserID(userID);
    }


    public List<Long> roleIdListBySigniner() {
        OwlUser signiner = SigninerUtil.getSigniner();
        List<Long> roleIdList = new ArrayList<>();
        owlUserDao.selectRoleByUserID(signiner.getId()).forEach(it -> roleIdList.add(it.getId()));
        return roleIdList;
    }
}
