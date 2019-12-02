package com.owl.util.model;

/**
 * String 包装类
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/11/18.
 */
public class OwlStringPg {
    private String msg;

    public OwlStringPg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean startsWith(String prefix) {
        return this.msg.startsWith(prefix);
    }

    public int indexOf(String str) {
        return this.msg.indexOf(str);
    }

    public String substring(int beginIndex) {
        return this.msg.substring(beginIndex).trim();
    }

    public String substring(int beginIndex, int endIndex) {
        return this.msg.substring(beginIndex, endIndex).trim();
    }

    public OwlStringPg update(int beginIndex) {
        this.msg = this.msg.substring(beginIndex).trim();
        return this;
    }

    public OwlStringPg update(String str) {
        this.msg = this.msg.substring(this.msg.indexOf(str) + 1).trim();
        return this;
    }

    public int length() {
        return this.msg.length();
    }
}
