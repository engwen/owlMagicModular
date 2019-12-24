package com.owl.shiro.conf;

import com.owl.common.CommentEM;
import com.owl.mvc.dto.RelationDTO;
import com.owl.mvc.vo.MsgResultVO;
import com.owl.shiro.model.*;
import com.owl.shiro.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/05/07.
 */
@Component
public class OwlShiroInit implements InitializingBean {

    //不需要登陸就可以訪問的接口
    private static final String auth = "^/auth/.*$";

    private static Boolean isNeedInit = false;

    private static final Logger logger = Logger.getLogger(OwlShiroInit.class.getName());

    @Resource
    private OwlUserService owlUserService;
    @Resource
    private OwlRoleService owlRoleService;
    @Resource
    private OwlPermissionService owlPermissionService;
    @Resource
    private OwlPageService owlPageService;
    @Resource
    private ReUserRoleService reUserRoleService;
    @Resource
    private ReRolePermissionService reRolePermissionService;
    @Resource
    private ReRolePageService reRolePageService;
    @Resource
    private RequestMappingHandlerMapping requestMappingHandlerMapping;
    @Resource
    private FilterChainDefinitionsService filterChainDefinitionsService;

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("project is start,now going init");
        //所有的權限集合
        List<OwlPermission> owlPermissionList = new ArrayList<>();
        //將要創建的角色
        OwlRole role = new OwlRole(CommentEM.Role.ADMIN.role);
        //管理員賬號
        OwlUser user = new OwlUser(CommentEM.Role.ADMIN.name, "321321", CommentEM.Role.ADMIN.account, "admin@admin.com", "18812345678", true);
        if (owlPermissionService.getAll(null).getResultData().size() == 0) {
            logger.info("there is no permission url,we will get all permissionUrl from springBean");
            requestMappingHandlerMapping.getHandlerMethods().keySet().forEach((info) -> {
                OwlPermission owlPermission = new OwlPermission(info.getPatternsCondition().toString().replaceAll("[\\[\\]]", ""));
                if (!owlPermission.getPermissionUrl().matches(auth)) {
                    owlPermissionList.add(owlPermission);
                }
            });
            MsgResultVO result = owlPermissionService.createList(owlPermissionList);
            if (!result.getResult()) {
                logger.info("create permissionList is error ,the information is " + result.getResultMsg());
            }
            isNeedInit = true;
        }
        if (!owlRoleService.isExist(role).getResult()) {
            logger.info("there is no role ,we will create admin role");
            owlRoleService.create(role);
            List<Long> permissionIDs = new ArrayList<>();
            owlPermissionService.getAll(null).getResultData().forEach(owlpermission -> permissionIDs.add(owlpermission.getId()));
            reRolePermissionService.insertRelation(RelationDTO.getInstance(role.getId(), permissionIDs));
            isNeedInit = true;
        } else {
            role = owlRoleService.details(role).getResultData();
        }
        if (owlUserService.getAll(null).getResultData().size() == 0) {
            logger.info("there is no user ,we will create admin user");
            owlUserService.create(user);
            OwlUserRole userRole = new OwlUserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(role.getId());
            reUserRoleService.insert(userRole);
            isNeedInit = true;
        }
        if (isNeedInit) {
            filterChainDefinitionsService.reloadFilterChains();
            isNeedInit = false;
        }
        if (owlPageService.getAll(null).getResultData().size() == 0) {
            logger.info("there is no page ,we will create default page");
            List<OwlPage> pageList = new ArrayList<>();
            pageList.add(listAddPageByName("用户管理"));
            pageList.add(listAddPageByName("角色管理"));
            pageList.add(listAddPageByName("权限管理"));
            pageList.add(listAddPageByName("页面管理"));
            owlPageService.createList(pageList);
            RelationDTO relationDTO = new RelationDTO();
            relationDTO.setId(role.getId());
            List<Long> idList = new ArrayList<>();
            relationDTO.setIdList(idList);
            owlPageService.getAll(null).getResultData().forEach(it -> idList.add(it.getId()));
            reRolePageService.insertRelation(relationDTO);
        }
    }

    private static OwlPage listAddPageByName(String name) {
        OwlPage owlPage = new OwlPage();
        owlPage.setName(name);
        return owlPage;
    }

}
