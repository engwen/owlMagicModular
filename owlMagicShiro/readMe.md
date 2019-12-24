owlShiro 简介
-------

*此包被用于认证系统以及双师课堂二期的权限管理模块，使用时，直接将基础包复制到目标项目，更改spring配置文件，并添加shiro的配置文件，将resources下的相关mapping复制到目标项目的mapping目录*

> 基础包
* com.owl.shiro  
###API
* auth/heartSkip 用于更新session
* auth/noSignin 未登录
* auth/permissionDefined 权限拒绝
* auth/signin 登陆接口
* auth/signout 登出
* auth/signup 注册接口
* auth/isExist 检测是否被注册
* auth/resetPassword 重置密码
------------
* permission/create 创建新的权限
* permission/delete 删除权限
* permission/deleteList 删除权限集合
* permission/update 更新权限
* permission/details 获取权限详情
* permission/list 获取权限列表  分页
------------
* role/create 创建新的角色
* role/delete 删除角色
* role/deleteList 删除角色集合
* role/update 更新角色
* role/details 获取角色详情
* role/list 获取角色列表  分页
* role/addPermissionListByRoleId 为角色添加权限集合
* role/deleteByRoleIDAndPermissionIDList 依据角色ID删除权限集合
* role/deleteAllPermissionByRoleID 获取角色列表  删除角色的所有权限
* role/updatePermissionIDListByRoleID 更新角色的所有权限


------------
* user/create 创建新的用户
* user/banOrLeave 删除用户
* user/banOrLeaveList 删除用户集合
* user/update 更新用户
* user/details 获取用户详情
* user/list 获取用户列表  分页
* user/checkPassword 检测用户密码是否正确
* user/addRoleListByUserID 为用户添加角色集合
* user/deleteByUserIDAndRoleIDList 依据用户ID删除角色集合
* user/deleteAllRoleByUserID 获取用户列表  删除用户的所有角色
* user/updateRoleIDListByUserID 更新用户的所有角色



    