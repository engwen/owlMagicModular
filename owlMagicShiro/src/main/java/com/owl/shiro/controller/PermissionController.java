package com.owl.shiro.controller;

import com.owl.comment.annotations.OwlCheckParams;
import com.owl.mvc.controller.CellBaseControllerAb;
import com.owl.mvc.dto.BanListDTO;
import com.owl.mvc.dto.DeleteDTO;
import com.owl.mvc.dto.PageDTO;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.mvc.vo.PageVO;
import com.owl.shiro.model.OwlPermission;
import com.owl.shiro.service.OwlPermissionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * time 2018/03/27.
 */
@RestController
@RequestMapping(value = "permission", method = RequestMethod.POST, consumes = "application/json")
public class PermissionController extends CellBaseControllerAb<OwlPermissionService,OwlPermission> {
    @Resource
    private OwlPermissionService owlPermissionService;

    @Override
    @RequestMapping("create")
    @OwlCheckParams(notNull = {"permissionUrl"})
    public MsgResultVO<OwlPermission> create(@RequestBody OwlPermission owlPermission) {
        return owlPermissionService.create(owlPermission);
    }

    @Override
    @RequestMapping("delete")
    @OwlCheckParams(notNull = {"id"})
    public MsgResultVO delete(@RequestBody OwlPermission owlPermission) {
        return owlPermissionService.delete(owlPermission);
    }

    @Override
    @RequestMapping("update")
    @OwlCheckParams(notNull = {"id", "permissionUrl"})
    public MsgResultVO<?> update(@RequestBody OwlPermission permission) {
        return owlPermissionService.update(permission);
    }

    @Override
    @RequestMapping("details")
    @OwlCheckParams(notNull = {"id"})
    public MsgResultVO<OwlPermission> details(@RequestBody OwlPermission permission) {
        return owlPermissionService.details(permission);
    }

    @Override
    @RequestMapping("list")
    public MsgResultVO<PageVO<OwlPermission>> list(@RequestBody PageDTO<OwlPermission> pageDTO) {
        return owlPermissionService.list(pageDTO);
    }
}
