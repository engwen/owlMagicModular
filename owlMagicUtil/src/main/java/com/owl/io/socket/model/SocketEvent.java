package com.owl.io.socket.model;

import com.owl.model.ModelPrototype;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/11/13.
 */
public class SocketEvent extends ModelPrototype {
    private String event;
    private String msg;

    public SocketEvent(String event, String msg) {
        this.event = event;
        this.msg = msg;
    }

    public SocketEvent(String event) {
        this.event = event;
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
