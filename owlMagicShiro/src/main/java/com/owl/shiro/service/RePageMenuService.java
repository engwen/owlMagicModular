package com.owl.shiro.service;

import com.owl.mvc.dto.RelationDTO;
import com.owl.mvc.service.RelationBaseServiceAb;
import com.owl.mvc.so.ModelSO;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.shiro.dao.OwlPageMenuDao;
import com.owl.shiro.model.OwlPageMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/5/5.
 */
@Service
public class RePageMenuService extends RelationBaseServiceAb<OwlPageMenuDao, OwlPageMenu> {
    @Resource
    private OwlPageMenuDao owlPageMenuDao;

    @Override
    public MsgResultVO update(RelationDTO relationDTO) {
        OwlPageMenu pageMenu = new OwlPageMenu();
        pageMenu.setPageId(relationDTO.getId());
        owlPageMenuDao.delete(ModelSO.getInstance(pageMenu));
        owlPageMenuDao.insertRelation(relationDTO);
        return MsgResultVO.getInstanceSuccess();
    }
}
