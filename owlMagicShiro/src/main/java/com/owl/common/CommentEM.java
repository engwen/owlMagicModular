package com.owl.common;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/4/30.
 */
public class CommentEM {
    public enum Role {
        ADMIN("系统管理员", "admin", "admin");
        public String role;
        public String name;
        public String account;

        Role(String name, String account, String role) {
            this.name = name;
            this.account = account;
            this.role = role;
        }
    }
}
