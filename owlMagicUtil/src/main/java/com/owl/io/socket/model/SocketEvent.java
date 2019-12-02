package com.owl.io.socket.model;

import com.owl.model.OwlEvent;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/11/13.
 */
public class SocketEvent extends OwlEvent {

    private String msg;

    public static final SocketEvent SERVER_REQUEST_SUCCESS = new SocketEvent("server_request_success");
    public static final SocketEvent SERVER_ADD_ROOM = new SocketEvent("server_room_add");
    public static final SocketEvent SERVER_CLOSE_ROOM = new SocketEvent("server_room_close");
    public static final SocketEvent SERVER_INNER_ROOM= new SocketEvent("server_room_inner");
    public static final SocketEvent SERVER_OUT_ROOM = new SocketEvent("server_room_out");
    public static final SocketEvent SERVER_CONNECT_SUCCESS = new SocketEvent("server_connect_success");
    public static final SocketEvent SERVER_CONNECT_ERROR = new SocketEvent("server_connect_error");

    public static final SocketEvent SERVER_CLIENT_DISCONNECT = new SocketEvent("server_client_disconnect");

    public SocketEvent(String event, String msg) {
        super.setEventName(event);
        this.msg = msg;
    }

    public SocketEvent(String event) {
        super.setEventName(event);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
