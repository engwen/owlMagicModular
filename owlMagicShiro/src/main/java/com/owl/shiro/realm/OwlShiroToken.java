package com.owl.shiro.realm;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/03/26.
 */
public class OwlShiroToken extends UsernamePasswordToken {
    private TokenType tokenType;//account-1,email-2,mobile-3

    public enum TokenType {
        ACCOUNT, EMAIL, MOBILE
    }

    private String passwordStr;

    public String getPasswordStr() {
        return passwordStr;
    }

    public void setPasswordStr(String passwordStr) {
        this.passwordStr = passwordStr;
    }

    public OwlShiroToken(String username, String password,TokenType tokenType) {
        super(username, password);
        this.passwordStr = password;
        this.tokenType = tokenType;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

}
