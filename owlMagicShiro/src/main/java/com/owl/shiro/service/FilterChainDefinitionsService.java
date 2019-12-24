package com.owl.shiro.service;

import com.owl.shiro.factory.ShiroPermissionFactory;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/05/08.
 */
@Service
public class FilterChainDefinitionsService {

    @Resource
    private ShiroPermissionFactory permissionFactory;

    public void reloadFilterChains() {
        //强制同步，控制线程安全
        synchronized (permissionFactory) {
            AbstractShiroFilter shiroFilter = null;
            try {
                shiroFilter = (AbstractShiroFilter) permissionFactory.getObject();
                PathMatchingFilterChainResolver resolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
                // 过滤管理器
                DefaultFilterChainManager manager = (DefaultFilterChainManager) resolver.getFilterChainManager();
                // 清除权限配置
                if (null != manager.getFilterChains() && !manager.getFilterChains().isEmpty()) {
                    manager.getFilterChains().clear();
                }
                if (null != permissionFactory.getFilterChainDefinitionMap() && !permissionFactory.getFilterChainDefinitionMap().isEmpty()) {
                    permissionFactory.getFilterChainDefinitionMap().clear();
                }
                // 重新设置权限   //传入配置中的filterchains
                permissionFactory.setFilterChainDefinitions(ShiroPermissionFactory.definitions);

                Map<String, String> chains = permissionFactory.getFilterChainDefinitionMap();
                //重新生成过滤链
                if (!CollectionUtils.isEmpty(chains)) {
                    for (Map.Entry<String, String> chain : chains.entrySet()) {
                        manager.createChain(chain.getKey(), chain.getValue().replace(" ", ""));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
