package com.owl.io;

import com.owl.model.ModelPrototype;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/11/13.
 */
public class SocketModel extends ModelPrototype {
    private String event;
    private String msg;

    public  SocketModel(String event, String msg) {
        this.event = event;
        this.msg = msg;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
