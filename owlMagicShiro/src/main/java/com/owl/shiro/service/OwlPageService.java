package com.owl.shiro.service;

import com.owl.mvc.dto.DeleteDTO;
import com.owl.mvc.dto.RelationDTO;
import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.service.CellBaseServiceAb;
import com.owl.mvc.so.ModelSO;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.shiro.dao.*;
import com.owl.shiro.model.OwlMenu;
import com.owl.shiro.model.OwlPage;
import com.owl.shiro.model.OwlPageMenu;
import com.owl.shiro.model.OwlRolePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/4/29.
 */
@Service
public class OwlPageService extends CellBaseServiceAb<OwlPageDao, OwlPage> {

    @Resource
    private OwlPageDao owlPageDao;
    @Resource
    private OwlMenuDao owlMenuDao;
    @Resource
    private OwlPageMenuDao owlPageMenuDao;
    @Resource
    private OwlRolePageDao owlRolePageDao;
    @Resource
    private OwlRoleMenuDao owlRoleMenuDao;

    @Override
    @Transactional
    public MsgResultVO delete(OwlPage model) {
        MsgResultVO<OwlPage> result = details(model);
        if (result.getResult()) {
            //删除页面
            owlPageDao.deleteBySelective(model);
            //刪除角色和頁面關係
            OwlRolePage owlRolePage = new OwlRolePage();
            owlRolePage.setPageId(model.getId());
            owlRolePageDao.delete(ModelSO.getInstance(owlRolePage));
            //查詢此頁面包含的按鈕菜單
            OwlPageMenu owlPageMenu = new OwlPageMenu();
            owlPageMenu.setPageId(model.getId());
            List<OwlPageMenu> owlPageMenus = owlPageMenuDao.selectBySelective(ModelSO.getInstance(owlPageMenu));
            //刪除頁面中的菜單
            List<Long> menuIdList = new ArrayList<>();
            owlPageMenus.forEach(it -> menuIdList.add(it.getMenuId()));
            if (menuIdList.size() > 0) {
                owlMenuDao.deleteByIdList(DeleteDTO.getInstance(menuIdList));
                //刪除角色和菜單的關係
                RelationDTO relationDTO = new RelationDTO();
                relationDTO.setIdList(menuIdList);
                owlRoleMenuDao.deleteRelation(relationDTO);
            }
            //刪除頁面和菜單的關係
            owlPageMenuDao.delete(ModelSO.getInstance(owlPageMenu));
            result.successResult();
        } else {
            result.errorResult(MsgConstant.REQUEST_NOT_EXITS);
        }
        return result;
    }

    public MsgResultVO<List<OwlMenu>> pageMenuList(OwlPage owlPage) {
        return MsgResultVO.getInstanceSuccess(new ArrayList<>(owlMenuDao.menuListByPage(owlPage)));
    }
}
