package com.owl.shiro.model;

import com.owl.pattern.observer.OwlObserved;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Date;

public class OwlUser extends OwlObserved {

    private Long id;

    private String name;

    private String password;

    private String account;

    private String email;

    private String mobile;

    private Boolean isBan;

    private Boolean status;

    private Date lastSigninTime;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Boolean getIsBan() {
        return isBan;
    }

    public void setIsBan(Boolean ban) {
        isBan = ban;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getLastSigninTime() {
        return lastSigninTime;
    }

    public void setLastSigninTime(Date lastSigninTime) {
        this.lastSigninTime = lastSigninTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public OwlUser() {
    }

    public OwlUser(String name, String password, String account, String email, String mobile, Boolean status) {
        this.name = name;
        this.password = password;
        this.account = account;
        this.email = email;
        this.mobile = mobile;
        this.status = status;
    }
}