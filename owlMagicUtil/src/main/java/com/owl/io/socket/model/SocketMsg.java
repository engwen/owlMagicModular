package com.owl.io.socket.model;

import com.owl.model.ModelPrototype;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/11/19.
 */
public class SocketMsg extends ModelPrototype {
    private SocketEvent event;
    private String senderId;
    private String accepterId;
    private String roomId;

    public static SocketMsg getInstance() {
        return new SocketMsg();
    }

    public SocketEvent getEvent() {
        return event;
    }

    public void setEvent(SocketEvent event) {
        this.event = event;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getAccepterId() {
        return accepterId;
    }

    public void setAccepterId(String accepterId) {
        this.accepterId = accepterId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
