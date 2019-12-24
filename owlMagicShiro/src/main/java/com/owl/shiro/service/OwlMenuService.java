package com.owl.shiro.service;


import com.owl.util.RegexUtil;
import com.owl.mvc.dto.RelationDTO;
import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.service.CellBaseServiceAb;
import com.owl.mvc.so.ModelSO;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.shiro.dao.OwlMenuDao;
import com.owl.shiro.dao.OwlPageMenuDao;
import com.owl.shiro.dao.OwlRoleMenuDao;
import com.owl.shiro.dto.OwlMenuDTO;
import com.owl.shiro.model.OwlMenu;
import com.owl.shiro.model.OwlPageMenu;
import com.owl.shiro.model.OwlRoleMenu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/05/30.
 */
@Service
public class OwlMenuService extends CellBaseServiceAb<OwlMenuDao, OwlMenu> {
    @Resource
    private OwlMenuDao owlMenuDao;
    @Resource
    private OwlRoleMenuDao owlRoleMenuDao;
    @Resource
    private OwlPageMenuDao owlPageMenuDao;

    @Transactional
    public MsgResultVO<OwlMenu> create(OwlMenuDTO model) {
        MsgResultVO<OwlMenu> resultVO = super.create(model);
        List<Long> temp = new ArrayList<>();
        temp.add(resultVO.getResultData().getId());
        if (!RegexUtil.isEmpty(model.getRoleId())) {
            owlRoleMenuDao.insertRelation(RelationDTO.getInstance(model.getRoleId(), temp));
        }
        if (!RegexUtil.isEmpty(model.getPageId())) {
            owlPageMenuDao.insertRelation(RelationDTO.getInstance(model.getPageId(), temp));
        }
        return resultVO;
    }


    @Override
    @Transactional
    public MsgResultVO delete(OwlMenu owlMenu) {
        MsgResultVO<OwlMenu> result = details(owlMenu);
        if (result.getResult()) {
            //移除所有的菜单信息
            owlMenuDao.deleteBySelective(owlMenu);
            //移除所有用戶擁有的此菜单信息
            OwlRoleMenu owlRoleMenu = new OwlRoleMenu();
            owlRoleMenu.setMenuId(result.getResultData().getId());
            owlRoleMenuDao.delete(ModelSO.getInstance(owlRoleMenu));
            //移除所有頁面擁有的此菜单信息
            OwlPageMenu owlPageMenu = new OwlPageMenu();
            owlPageMenu.setMenuId(result.getResultData().getId());
            owlPageMenuDao.delete(ModelSO.getInstance(owlPageMenu));
            result.successResult();
        } else {
            result.errorResult(MsgConstant.REQUEST_NOT_EXITS);
        }
        return result;
    }

}
