package com.owl.socket;

import java.nio.channels.AsynchronousSocketChannel;
import java.util.List;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/11/16.
 */
public class SocketRoom {
    Integer roomId;
    List<AsynchronousSocketChannel> socketChannelList;

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public List<AsynchronousSocketChannel> getSocketChannelList() {
        return socketChannelList;
    }

    public void setSocketChannelList(List<AsynchronousSocketChannel> socketChannelList) {
        this.socketChannelList = socketChannelList;
    }
}
