package com.owl.shiro.factory;

import com.owl.shiro.model.OwlPermission;
import com.owl.shiro.service.OwlPermissionService;
import com.owl.shiro.service.OwlRoleService;
import org.apache.shiro.config.Ini;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * 动态权限生成
 * @author engwen
 * email xiachanzou@outlook.com
 * time 2018/03/28.
 */
public class ShiroPermissionFactory extends ShiroFilterFactoryBean {
    /**
     * 配置中的过滤链
     */
    public static String definitions;
    @Resource
    private OwlRoleService roleService;
    @Resource
    private OwlPermissionService owlPermissionService;

    /**
     * 从数据库动态读取权限
     */
    @Override
    public void setFilterChainDefinitions(String definitions) {
        //数据库动态权限
        List<OwlPermission> permissions = owlPermissionService.getAll(null).getResultData();
        for (OwlPermission po : permissions) {
            StringBuilder sb = new StringBuilder();
            sb.append("roles[");
            roleService.findRoleByPermissionId(po.getId()).forEach((role) -> {
                sb.append(role.getRole());
                sb.append(",");
            });
            if (sb.length() >= 7) {
                //字符串拼接权限
                definitions = definitions + po.getPermissionUrl() + " = " + sb.substring(0, sb.length() - 1) + "]\n";
            }
        }
        ShiroPermissionFactory.definitions = definitions;
        //从配置文件加载权限配置
        Ini ini = new Ini();
        ini.load(definitions);
        Ini.Section section = ini.getSection(IniFilterChainResolverFactory.URLS);
        if (CollectionUtils.isEmpty(section)) {
            section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
        }
        //加入权限集合
        setFilterChainDefinitionMap(section);
    }
}
