package com.owl.io.socket;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * 房间信息
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/11/16.
 */
public class SocketRoom {
    private String roomId;
    private Set<SocketClient> socketClientSet;


    public SocketRoom(String roomId) {
        this.roomId = roomId;
        socketClientSet = new HashSet<>();
    }

    public void join(SocketClient socketChannel) {
        socketClientSet.add(socketChannel);
    }

    public void leave(SocketClient socketClient) {
        Optional<SocketClient> any = socketClientSet.stream().filter(it -> it.getUuid().equals(socketClient.getUuid())).findAny();
        any.ifPresent(it -> socketClientSet.remove(it));
    }

    public void close() {
        roomId = null;
        socketClientSet = null;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Set<SocketClient> getSocketClientSet() {
        return socketClientSet;
    }

    public void setSocketClientSet(Set<SocketClient> socketClientSet) {
        this.socketClientSet = socketClientSet;
    }
}
