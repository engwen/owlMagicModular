package com.owl.io.socket;

import com.owl.io.socket.model.SocketEvent;
import com.owl.io.socket.model.SocketMsg;
import com.owl.util.LogPrintUtil;
import com.owl.util.ObjectUtil;
import com.owl.util.RandomUtil;

import java.nio.channels.AsynchronousSocketChannel;
import java.util.*;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/11/19.
 */
public class SocketDispatchEvent {

    //this channel have room list
    private static List<SocketRoom> socketRoomList = new ArrayList<>();
    //this channel have client list
    private static Set<SocketClient> socketClientSet = new HashSet<>();

    /*
     * 判斷是否是第一次鏈接
     * @param socketChannel
     * @return
     */
    private static SocketMsg addToSocketClientSet(AsynchronousSocketChannel socketChannel) {
        SocketMsg socketMsg = SocketMsg.getInstance();
        Optional<SocketClient> clientOptional = socketClientSet.stream().filter(it -> it.getSocketChannel().equals(socketChannel)).findAny();
        if (clientOptional.isPresent()) {
            socketMsg.setSenderId(clientOptional.get().getUuid());
        } else {
            SocketClient socketClient = new SocketClient();
            socketClient.setSocketChannel(socketChannel);
            while (true) {
                String uuid = RandomUtil.uuid();
                Optional<SocketClient> any = socketClientSet.stream().filter(it -> it.getUuid().equals(uuid)).findAny();
                if (!any.isPresent()) {
                    socketClient.setUuid(uuid);
                    break;
                }
            }
            socketClientSet.add(socketClient);
            socketMsg.setSenderId(socketClient.getUuid());
        }
        return socketMsg;
    }

    public static void dispatchEvent(AsynchronousSocketChannel socketChannel, Map msg) {
        SocketMsg model = addToSocketClientSet(socketChannel);
        if (null != msg && null != msg.get("event")) {
            model.setEvent(new SocketEvent(msg));
        }
        LogPrintUtil.info("dispatch event success. msg is " + ObjectUtil.toJSON(model));
    }

    public static void dispatchEvent(AsynchronousSocketChannel socketChannel, SocketEvent event) {
        SocketMsg model = addToSocketClientSet(socketChannel);
        model.setEvent(event);
        LogPrintUtil.info("dispatch event success. msg is " + ObjectUtil.toJSON(model));
    }


    public static void addRoom(String roomId) {
        Optional<SocketRoom> any = socketRoomList.stream().filter(it -> it.getRoomId().equals(roomId)).findAny();
        if (!any.isPresent()) {
            SocketRoom socketRoom = new SocketRoom(roomId);
            socketRoomList.add(socketRoom);
        }
    }

    public static void removeRoom(String roomId) {
        Optional<SocketRoom> any = socketRoomList.stream().filter(it -> it.getRoomId().equals(roomId)).findAny();
        any.ifPresent(socketRoom -> {
            socketRoomList.remove(socketRoom);
            socketRoom.close();
        });
    }

    public static void addClient(SocketClient client) {
        Optional<SocketClient> any = socketClientSet.stream().filter(it -> it.getUuid().equals(client.getUuid())).findAny();
        if (!any.isPresent()) {
            socketClientSet.add(client);
        }
    }

    public static void removeClient(SocketClient client) {
        Optional<SocketClient> any = socketClientSet.stream().filter(it -> it.getUuid().equals(client.getUuid())).findAny();
        any.ifPresent(it -> socketClientSet.remove(client));
    }

    public static void clear() {
        socketRoomList = null;
        socketClientSet = null;
    }
}
