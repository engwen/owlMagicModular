package com.owl.io.socket;

import com.owl.io.socket.model.SocketMsg;
import com.owl.io.socket.server.SocketDispatch;
import com.owl.util.LogUtil;
import com.owl.util.ObjectUtil;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.*;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/11/19.
 */
public class SocketDispatchJavaServer implements SocketDispatch {

    //this channel have room list
    private List<SocketRoom> socketRoomList = new ArrayList<>();
    //this channel have client list
    private Set<SocketClient> socketClientSet = new HashSet<>();

    public List<SocketRoom> getSocketRoomList() {
        return socketRoomList;
    }

    public Set<SocketClient> getSocketClientSet() {
        return socketClientSet;
    }


    /*
     * 判斷是否是第一次鏈接
     * @param socketChannel
     * @return
     */
    private void addToSocketClientSet(AsynchronousSocketChannel socketChannel) {
        String uuid = "";
        try {
            uuid = socketChannel.getRemoteAddress().toString().replace("/", "")
                    .replace(".", "").replace(":", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Optional<SocketClient> clientOptional = socketClientSet.stream().filter(it -> it.getSocketChannel().equals(socketChannel)).findAny();
        if (!clientOptional.isPresent()) {
            SocketClient socketClient = new SocketClient();
            socketClient.setSocketChannel(socketChannel);
            socketClient.setUuid(uuid);
            socketClientSet.add(socketClient);
        }
    }

    public void dispatchEvent(AsynchronousSocketChannel socketChannel, SocketMsg msg) {
        addToSocketClientSet(socketChannel);
        LogUtil.info("dispatch event success. msg is " + ObjectUtil.toJSON(msg));
    }


    public void addRoom(String roomId) {
        Optional<SocketRoom> any = socketRoomList.stream().filter(it -> it.getRoomId().equals(roomId)).findAny();
        if (!any.isPresent()) {
            SocketRoom socketRoom = new SocketRoom(roomId);
            socketRoomList.add(socketRoom);
        }
    }

    public void removeRoom(String roomId) {
        Optional<SocketRoom> any = socketRoomList.stream().filter(it -> it.getRoomId().equals(roomId)).findAny();
        any.ifPresent(socketRoom -> {
            socketRoomList.remove(socketRoom);
            socketRoom.close();
        });
    }

    public void addClient(SocketClient client) {
        Optional<SocketClient> any = socketClientSet.stream().filter(it -> it.getUuid().equals(client.getUuid())).findAny();
        if (!any.isPresent()) {
            socketClientSet.add(client);
        }
    }

    public void removeClient(SocketClient client) {
        Optional<SocketClient> any = socketClientSet.stream().filter(it -> it.getUuid().equals(client.getUuid())).findAny();
        any.ifPresent(it -> socketClientSet.remove(client));
    }

    public void clear() {
        socketRoomList = null;
        socketClientSet = null;
    }
}
