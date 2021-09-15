package com.owl.io.socket.model;

import com.owl.model.OwlEvent;

import java.util.Map;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/11/13.
 */
public class SocketEvent extends OwlEvent {

    private Map<String, String> msg;

    public static final SocketEvent SERVER_REQUEST_SUCCESS = new SocketEvent("toClient:request:success");
    public static final SocketEvent SERVER_CONNECT_SUCCESS = new SocketEvent("toClient:connect:success");
    public static final SocketEvent SERVER_CONNECT_ERROR = new SocketEvent("toClient:connect:error");
    public static final SocketEvent CLIENT_CONNECT_SUCCESS = new SocketEvent("toServer:connect:success");
    public static final SocketEvent SERVER_CLIENT_DISCONNECT = new SocketEvent("toServer:connect:disconnect");

    public SocketEvent(String event, Map<String, String> msg) {
        super.setEventName(event);
        this.msg = msg;
    }

    public SocketEvent(Map<String, String> msg) {
        if (null != msg.get("eventName"))
            super.setEventName(String.valueOf(msg.get("eventName")));
        if (null != msg.get("msg"))
            this.msg = msg;
    }

    public SocketEvent(String event) {
        super.setEventName(event);
    }

    public Map<String, String> getMsg() {
        return msg;
    }

    public void setMsg(Map msg) {
        this.msg = msg;
    }

}
