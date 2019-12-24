package com.owl.shiro.controller;

import com.owl.comment.annotations.OwlCheckParams;
import com.owl.comment.annotations.OwlLogInfo;
import com.owl.comment.annotations.OwlSetNullData;
import com.owl.util.RegexUtil;
import com.owl.mvc.controller.CellBaseControllerAb;
import com.owl.mvc.dto.BanDTO;
import com.owl.mvc.dto.BanListDTO;
import com.owl.mvc.dto.PageDTO;
import com.owl.mvc.dto.RelationDTO;
import com.owl.mvc.model.MsgConstant;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.mvc.vo.PageVO;
import com.owl.shiro.model.OwlUser;
import com.owl.shiro.model.OwlUserRole;
import com.owl.shiro.service.OwlAuthService;
import com.owl.shiro.service.OwlRoleService;
import com.owl.shiro.service.OwlUserService;
import com.owl.shiro.service.ReUserRoleService;
import com.owl.shiro.util.SigninerUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * time 2018/03/26.
 */
@OwlLogInfo
@RestController
@RequestMapping(value = "user", method = RequestMethod.POST, consumes = "application/json")
public class UserController extends CellBaseControllerAb<OwlUserService,OwlUser> {
    @Resource
    private OwlUserService owlUserService;
    @Resource
    private OwlRoleService owlRoleService;
    @Resource
    private ReUserRoleService reUserRoleService;
    @Resource
    private OwlAuthService owlAuthService;

    @Override
    @RequestMapping("create")
    @OwlCheckParams(notNull = {"password", "name"}, notAllNull = {"account", "email", "mobile"})
    public MsgResultVO<OwlUser> create(@RequestBody OwlUser user) {
        MsgResultVO<OwlUser> result = new MsgResultVO<>();
        if (!RegexUtil.isEmpty(user.getEmail()) && !RegexUtil.isEmail(user.getEmail())) {
            result.errorResult(MsgConstant.PARAM_EMAIL_ERROR);
        } else if (!RegexUtil.isEmpty(user.getMobile()) && !RegexUtil.isMobile(user.getMobile())) {
            result.errorResult(MsgConstant.PARAM_MOBILE_ERROR);
        } else if (!RegexUtil.isEmpty(user.getAccount()) && RegexUtil.isEmpty(user.getAccount())) {
            result.errorResult(MsgConstant.PARAM_ACCOUNT_ERROR);
        } else {
            result = owlUserService.create(user);
        }
        return result;
    }

    @Override
    @RequestMapping("delete")
    @OwlCheckParams(notNull = {"id"})
    public MsgResultVO delete(@RequestBody OwlUser model) {
        return owlUserService.delete(model);
    }

    @Override
    @RequestMapping("banOrLeave")
    @OwlCheckParams(notNull = {"id", "isBan"})
    public MsgResultVO banOrLeave(@RequestBody BanDTO banDTO) {
        return owlUserService.banOrLeave(banDTO);
    }

    @Override
    @RequestMapping("banOrLeaveList")
    @OwlCheckParams(notNull = {"idList", "isBan"})
    public MsgResultVO banOrLeaveList(@RequestBody BanListDTO banDTO) {
        return owlUserService.banOrLeaveList(banDTO);
    }

    @Override
    @RequestMapping("update")
    @OwlCheckParams(notNull = {"id"})
    @OwlSetNullData(backValue = {"password"})
    public MsgResultVO update(@RequestBody OwlUser user) {
        MsgResultVO<OwlUser> result = new MsgResultVO<>();
        if (!RegexUtil.isEmpty(user.getEmail()) && !RegexUtil.isEmail(user.getEmail())) {
            result.errorResult(MsgConstant.PARAM_EMAIL_ERROR);
        }
        if (!RegexUtil.isEmpty(user.getMobile()) && RegexUtil.isMobile(user.getMobile())) {
            result.errorResult(MsgConstant.PARAM_MOBILE_ERROR);
        }
        if (!RegexUtil.isEmpty(user.getAccount()) && RegexUtil.isEmpty(user.getAccount())) {
            result.errorResult(MsgConstant.PARAM_ACCOUNT_ERROR);
        }
        if (result.getResult()) {
            return owlUserService.update(user);
        }
        return result;
    }

    @Override
    @RequestMapping("details")
    @OwlCheckParams(notNull = {"id"})
    public MsgResultVO<OwlUser> details(@RequestBody OwlUser user) {
        if (null == user.getStatus()) {
            user.setStatus(true);
        }
        return owlUserService.details(user);
    }

    @Override
    @RequestMapping(value = "list")
    @OwlSetNullData(backValue = {"password"})
    public MsgResultVO<PageVO<OwlUser>> list(@RequestBody PageDTO<OwlUser> pageDTO) {
        return owlUserService.list(pageDTO);
    }

    /**
     * 檢測用戶 密碼是否正確
     * @param user
     * @return
     */
    @RequestMapping("checkPassword")
    @OwlCheckParams(notNull = {"password"}, notAllNull = {"account", "email", "mobile"})
    public MsgResultVO checkPassword(@RequestBody OwlUser user) {
        return owlAuthService.checkPassword(user);
    }

    /**
     * 给用户添加角色
     * @param userRole
     * @return
     */
    @RequestMapping("addRole")
    @OwlCheckParams(notNull = {"userId", "roleId"})
    public MsgResultVO addRole(@RequestBody OwlUserRole userRole) {
        return reUserRoleService.insert(userRole);
    }

    /**
     * 给用户添加角色
     * @param relationDTO
     * @return
     */
    @RequestMapping("addRoleList")
    @OwlCheckParams(notNull = {"id", "idList"})
    public MsgResultVO addRoleList(@RequestBody RelationDTO relationDTO) {
        return reUserRoleService.insertRelation(relationDTO);
    }

    /**
     * 删除用户指定角色
     * @param userRole
     * @return
     */
    @RequestMapping("removeRole")
    @OwlCheckParams(notNull = {"userId", "roleId"})
    public MsgResultVO removeRole(@RequestBody OwlUserRole userRole) {
        return reUserRoleService.delete(userRole);
    }

    /**
     * 删除用户指定角色
     * @param relationDTO
     * @return
     */
    @RequestMapping("removeRoleList")
    @OwlCheckParams(notNull = {"id", "idList"})
    public MsgResultVO removeRoleList(@RequestBody RelationDTO relationDTO) {
        return reUserRoleService.deleteRelation(relationDTO);
    }


    /**
     * 更新用戶角色集合
     * @param relationDTO
     * @return
     */
    @RequestMapping("updateRoleList")
    @OwlCheckParams(notNull = {"id", "idList"})
    public MsgResultVO updateRoleList(@RequestBody RelationDTO relationDTO) {
        return reUserRoleService.update(relationDTO);
    }


    /**
     * 获取登录人员的角色信息
     * @return
     */
    @RequestMapping("getRoleList")
    public MsgResultVO getRoleList() {
        MsgResultVO<List> resultVO = new MsgResultVO<>();
        OwlUser signiner = SigninerUtil.getSigniner();
        if (null != signiner.getId()) {
            resultVO.successResult(owlUserService.roleListByUserID(signiner.getId()));
        }
        return resultVO;
    }

    /**
     * 获取登录人员可以访问的页面以及菜单信息
     * @return
     */
    @RequestMapping("getPageList")
    public MsgResultVO getPageList() {
        MsgResultVO<List> resultVO = new MsgResultVO<>();
        List<Long> roleIdList = owlUserService.roleIdListBySigniner();
        if (roleIdList.size() > 0) {
            resultVO.successResult(owlRoleService.pageMenuListByRole(roleIdList));
        }
        return resultVO;
    }
}
