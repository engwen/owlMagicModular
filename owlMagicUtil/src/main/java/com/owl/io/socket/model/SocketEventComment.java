package com.owl.io.socket.model;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/11/19.
 */
public interface SocketEventComment {

    SocketEvent REQUEST_SUCCESS_EVENT = new SocketEvent("request_success_event");
    SocketEvent ADD_ROOM_EVENT = new SocketEvent("room_add_event");
    SocketEvent CLOSE_ROOM_EVENT = new SocketEvent("room_close_event");
    SocketEvent INNER_ROOM_EVENT = new SocketEvent("room_inner_event");
    SocketEvent OUT_ROOM_EVENT = new SocketEvent("room_out_event");

}
