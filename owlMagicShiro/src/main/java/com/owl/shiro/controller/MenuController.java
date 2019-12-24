package com.owl.shiro.controller;

import com.owl.comment.annotations.OwlCheckParams;
import com.owl.mvc.controller.CellBaseControllerAb;
import com.owl.mvc.dto.BanListDTO;
import com.owl.mvc.dto.DeleteDTO;
import com.owl.mvc.dto.PageDTO;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.mvc.vo.PageVO;
import com.owl.shiro.dao.OwlMenuDao;
import com.owl.shiro.dto.OwlMenuDTO;
import com.owl.shiro.model.OwlMenu;
import com.owl.shiro.service.OwlMenuService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2018/05/30.
 */
@RestController
@RequestMapping(value = "menu", method = RequestMethod.POST, consumes = "application/json")
public class MenuController extends CellBaseControllerAb<OwlMenuService, OwlMenu> {
    @Resource
    private OwlMenuService owlMenuService;

    @Override
    public MsgResultVO deleteList(DeleteDTO deleteDTO) {
        return super.deleteList(deleteDTO);
    }

    @RequestMapping("create")
    @OwlCheckParams(notNull = {"name"})
    public MsgResultVO<OwlMenu> create(@RequestBody OwlMenuDTO owlMenu) {
        return owlMenuService.create(owlMenu);
    }

    @Override
    @RequestMapping("delete")
    @OwlCheckParams(notNull = {"id"})
    public MsgResultVO delete(@RequestBody OwlMenu owlMenu) {
        return owlMenuService.delete(owlMenu);
    }

    @Override
    @RequestMapping("update")
    @OwlCheckParams(notNull = "id")
    public MsgResultVO<?> update(@RequestBody OwlMenu owlMenu) {
        return owlMenuService.update(owlMenu);
    }

    @Override
    @RequestMapping("details")
    @OwlCheckParams(notNull = {"id"})
    public MsgResultVO<OwlMenu> details(@RequestBody OwlMenu owlMenu) {
        return owlMenuService.details(owlMenu);
    }

    @Override
    @RequestMapping("list")
    public MsgResultVO<PageVO<OwlMenu>> list(@RequestBody PageDTO<OwlMenu> pageDTO) {
        return super.list(pageDTO);
    }
}
