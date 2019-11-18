package com.owl.socket.model;

import com.owl.io.SocketServer;
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

    public static SocketEvent CONNECT_SERVER = new SocketEvent("server_connect_server", "connect success");
    public static SocketEvent SERVER_IN_ROOM = new SocketEvent("server_join_room", "join success");
    public static SocketEvent OUT_ROOM = new SocketEvent("server_leave_room", "leave success");

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
