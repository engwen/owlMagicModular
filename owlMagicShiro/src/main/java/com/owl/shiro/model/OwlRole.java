package com.owl.shiro.model;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/03/26.
 */
public class OwlRole {
    //角色ID
    private Long id;
    //角色名称
    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public OwlRole() {
    }

    public OwlRole(String role) {
        this.role = role;
    }
}
