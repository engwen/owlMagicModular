SET FOREIGN_KEY_CHECKS=0;

DROP DATABASE IF EXISTS `db_shiro_prod`;

CREATE DATABASE `db_shiro_prod`
    CHARACTER SET 'utf8'
    COLLATE 'utf8_general_ci';

USE `db_shiro_prod`;

-- ----------------------------
-- Table structure for `owl_user`
-- ----------------------------
DROP TABLE IF EXISTS `owl_user`;
CREATE TABLE `owl_user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `account` varchar(255) DEFAULT NULL COMMENT '账号',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱地址',
  `mobile` varchar(36) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(1) NOT NULL DEFAULT TRUE COMMENT '是否有效',
  `is_ban` tinyint(1) NOT NULL DEFAULT FALSE COMMENT '是否有效',
  `last_signin_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户基础表';

-- ----------------------------
-- Table structure for `owl_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `owl_user_role`;
CREATE TABLE `owl_user_role` (
  `user_id` bigint(11) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(11) DEFAULT NULL COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';

-- ----------------------------
-- Table structure for `owl_role`
-- ----------------------------
DROP TABLE IF EXISTS `owl_role`;
CREATE TABLE `owl_role` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(50) DEFAULT NULL COMMENT '角色',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色基础表';

-- ----------------------------
-- Table structure for `owl_permission`
-- ----------------------------
DROP TABLE IF EXISTS `owl_permission`;
CREATE TABLE `owl_permission` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `permission_url` varchar(255) DEFAULT NULL COMMENT '权限',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限基础表';

-- ---------------------------tiyint-
-- Table structure for `owl_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `owl_role_permission`;
CREATE TABLE `owl_role_permission` (
  `role_id` bigint(11) DEFAULT NULL COMMENT '角色ID',
  `permission_id`  bigint(11) DEFAULT NULL COMMENT '权限ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限关系表';

-- ----------------------------
-- Table structure for `owl_menu`
-- ----------------------------
DROP TABLE IF EXISTS `owl_menu`;
CREATE TABLE `owl_menu` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name`  varchar(255) DEFAULT NULL COMMENT '按钮名称',
  `btn_id`  varchar(255) DEFAULT NULL COMMENT '按钮id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单基础表';

-- ----------------------------
-- Table structure for `owl_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `owl_role_menu`;
CREATE TABLE `owl_role_menu` (
  `role_id` bigint(11) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(11) DEFAULT NULL COMMENT '菜单ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单权限关系表';

-- ----------------------------
-- Table structure for `owl_page`
-- ----------------------------
DROP TABLE IF EXISTS `owl_page`;
CREATE TABLE `owl_page` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='页面基础表';

-- ----------------------------
-- Table structure for `owl_role_page`
-- ----------------------------
DROP TABLE IF EXISTS `owl_role_page`;
CREATE TABLE `owl_role_page` (
  `role_id` bigint(11) DEFAULT NULL COMMENT '角色ID',
  `page_id` bigint(11) DEFAULT NULL COMMENT '页面ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='页面权限关系表';


-- ----------------------------
-- Table structure for `owl_page_menu`
-- ----------------------------
DROP TABLE IF EXISTS `owl_page_menu`;
CREATE TABLE `owl_page_menu` (
  `page_id` bigint(11) DEFAULT NULL COMMENT '页面ID',
  `menu_id` bigint(11) DEFAULT NULL COMMENT '菜單ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='页面权限关系表';