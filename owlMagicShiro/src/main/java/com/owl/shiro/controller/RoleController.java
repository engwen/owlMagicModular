package com.owl.shiro.controller;

import com.owl.comment.annotations.OwlCheckParams;
import com.owl.mvc.controller.CellBaseControllerAb;
import com.owl.mvc.dto.PageDTO;
import com.owl.mvc.dto.RelationDTO;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.mvc.vo.PageVO;
import com.owl.shiro.model.OwlRole;
import com.owl.shiro.model.OwlRoleMenu;
import com.owl.shiro.model.OwlRolePage;
import com.owl.shiro.model.OwlRolePermission;
import com.owl.shiro.service.ReRoleMenuService;
import com.owl.shiro.service.ReRolePageService;
import com.owl.shiro.service.ReRolePermissionService;
import com.owl.shiro.service.OwlRoleService;
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
@RequestMapping(value = "role", method = RequestMethod.POST, consumes = "application/json")
public class RoleController extends CellBaseControllerAb<OwlRoleService,OwlRole> {
    @Resource
    private OwlRoleService owlRoleService;
    @Resource
    private ReRoleMenuService reRoleMenuService;
    @Resource
    private ReRolePermissionService reRolePermissionService;
    @Resource
    private ReRolePageService reRolePageService;

    @Override
    @RequestMapping("create")
    @OwlCheckParams(notNull = {"role"})
    public MsgResultVO<OwlRole> create(@RequestBody OwlRole role) {
        return owlRoleService.create(role);
    }

    @Override
    @RequestMapping("delete")
    @OwlCheckParams(notNull = {"id"})
    public MsgResultVO delete(@RequestBody OwlRole owlRole) {
        return owlRoleService.delete(owlRole);
    }


    @Override
    @RequestMapping("update")
    @OwlCheckParams(notNull = {"id", "role"})
    public MsgResultVO<?> update(@RequestBody OwlRole role) {
        return owlRoleService.update(role);
    }

    @Override
    @RequestMapping("details")
    @OwlCheckParams(notNull = {"id"})
    public MsgResultVO<OwlRole> details(@RequestBody OwlRole role) {
        return owlRoleService.details(role);
    }

    @Override
    @RequestMapping("list")
    public MsgResultVO<PageVO<OwlRole>> list(@RequestBody PageDTO<OwlRole> pageDTO) {
        return owlRoleService.list(pageDTO);
    }

    /*--------------------------------------   角色權限   ---------------------------------------*/

    /**
     * 获取角色对应的权限
     * @param rolePermission 角色ID  權限ID集合
     * @return
     */
    @RequestMapping("rolePermissionList")
    @OwlCheckParams(notNull = {"roleId"})
    public MsgResultVO rolePermissionList(@RequestBody OwlRolePermission rolePermission) {
        return owlRoleService.rolePermissionList(rolePermission);
    }

    /**
     * 為角色添加權限
     * @param rolePermission 角色ID  權限ID集合
     * @return
     */
    @RequestMapping("addPermission")
    @OwlCheckParams(notNull = {"roleId", "permissionId"})
    public MsgResultVO addPermission(@RequestBody OwlRolePermission rolePermission) {
        return reRolePermissionService.insert(rolePermission);
    }

    /**
     * 為角色添加權限
     * @param relationDTO 角色ID  權限ID集合
     * @return
     */
    @RequestMapping("addPermissionList")
    @OwlCheckParams(notNull = {"id", "idList"})
    public MsgResultVO addPermissionList(@RequestBody RelationDTO relationDTO) {
        return reRolePermissionService.insertRelation(relationDTO);
    }

    /**
     * 刪除角色的權限
     * @param rolePermission 角色ID  權限ID集合
     * @return
     */
    @RequestMapping("removePermission")
    @OwlCheckParams(notNull = {"roleId", "permissionId"})
    public MsgResultVO removePermission(@RequestBody OwlRolePermission rolePermission) {
        return reRolePermissionService.delete(rolePermission);
    }

    /**
     * 刪除角色的權限集合
     * @param relationDTO 角色ID  權限ID集合
     * @return
     */
    @RequestMapping("removePermissionList")
    @OwlCheckParams(notNull = {"id", "idList"})
    public MsgResultVO removePermissionList(@RequestBody RelationDTO relationDTO) {
        return reRolePermissionService.deleteRelation(relationDTO);
    }

    /**
     * 更新角色的權限集合
     * @param relationDTO 角色ID  權限ID集合
     * @return
     */
    @RequestMapping("updatePermissionList")
    @OwlCheckParams(notNull = {"id", "idList"})
    public MsgResultVO updatePermissionList(@RequestBody RelationDTO relationDTO) {
        return reRolePermissionService.update(relationDTO);
    }


    /*--------------------------------------   角色菜單   ---------------------------------------*/
    /**
     * 获取角色对应的菜单
     * @param roleMenu 角色ID  權限ID集合
     * @return
     */
    @RequestMapping("roleMenuList")
    @OwlCheckParams(notNull = {"roleId"})
    public MsgResultVO roleMenuList(@RequestBody OwlRoleMenu roleMenu) {
        return owlRoleService.roleMenuList(roleMenu);
    }
    /**
     * 為角色添加菜单
     * @param roleMenu
     * @return
     */
    @RequestMapping("addMenu")
    @OwlCheckParams(notNull = {"roleId", "menuId"})
    public MsgResultVO addMenu(@RequestBody OwlRoleMenu roleMenu) {
        return reRoleMenuService.insert(roleMenu);
    }

    /**
     * 為角色添加菜单
     * @param relationDTO
     * @return
     */
    @RequestMapping("addMenuList")
    @OwlCheckParams(notNull = {"id", "idList"})
    public MsgResultVO addMenuList(@RequestBody RelationDTO relationDTO) {
        return reRoleMenuService.insertRelation(relationDTO);
    }

    /**
     * 刪除角色的菜单
     * @return
     */
    @RequestMapping("removeMenu")
    @OwlCheckParams(notNull = {"roleId", "menuId"})
    public MsgResultVO removeMenu(@RequestBody OwlRoleMenu owlRoleMenu) {
        return reRoleMenuService.delete(owlRoleMenu);
    }

    /**
     * 刪除角色的菜单集合
     * @param relationDTO
     * @return
     */
    @RequestMapping("removeMenuList")
    @OwlCheckParams(notNull = {"id", "idList"})
    public MsgResultVO removeMenuList(@RequestBody RelationDTO relationDTO) {
        return reRoleMenuService.deleteRelation(relationDTO);
    }

    /**
     * 更新角色的權限集合
     * @param relationDTO 角色ID 菜单ID集合
     * @return
     */
    @RequestMapping("updateMenuList")
    @OwlCheckParams(notNull = {"id", "idList"})
    public MsgResultVO updateMenuList(@RequestBody RelationDTO relationDTO) {
        return reRoleMenuService.update(relationDTO);
    }

    /*--------------------------------------   角色頁面   ---------------------------------------*/

    /**
     * 获取角色对应的页面
     * @param rolePage 角色ID  權限ID集合
     * @return
     */
    @RequestMapping("rolePageList")
    @OwlCheckParams(notNull = {"roleId"})
    public MsgResultVO rolePageList(@RequestBody OwlRolePage rolePage) {
        return owlRoleService.rolePageList(rolePage);
    }

    /**
     * 為角色添加菜单
     * @param rolePage
     * @return
     */
    @RequestMapping("addPage")
    @OwlCheckParams(notNull = {"roleId", "pageId"})
    public MsgResultVO addPage(@RequestBody OwlRolePage rolePage) {
        return reRolePageService.insert(rolePage);
    }

    /**
     * 為角色添加菜单
     * @param relationDTO
     * @return
     */
    @RequestMapping("addPageList")
    @OwlCheckParams(notNull = {"id", "idList"})
    public MsgResultVO addPageList(@RequestBody RelationDTO relationDTO) {
        return reRolePageService.insertRelation(relationDTO);
    }

    /**
     * 刪除角色的菜单
     * @return
     */
    @RequestMapping("removePage")
    @OwlCheckParams(notNull = {"roleId", "pageId"})
    public MsgResultVO removePage(@RequestBody OwlRolePage rolePage) {
        return reRolePageService.delete(rolePage);
    }

    /**
     * 刪除角色的菜单集合
     * @param relationDTO
     * @return
     */
    @RequestMapping("removePageList")
    @OwlCheckParams(notNull = {"id", "idList"})
    public MsgResultVO removePageList(@RequestBody RelationDTO relationDTO) {
        return reRolePageService.deleteRelation(relationDTO);
    }

    /**
     * 更新角色的權限集合
     * @param relationDTO 角色ID 菜单ID集合
     * @return
     */
    @RequestMapping("updatePageList")
    @OwlCheckParams(notNull = {"id", "idList"})
    public MsgResultVO updatePageList(@RequestBody RelationDTO relationDTO) {
        return reRolePageService.update(relationDTO);
    }
}
