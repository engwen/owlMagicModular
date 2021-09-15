package com.owl.io.socket;

import com.owl.io.socket.model.SocketEvent;
import com.owl.io.socket.model.SocketMsg;
import com.owl.io.socket.server.SocketDispatch;
import com.owl.util.ConsolePrintUtil;
import com.owl.util.ObjectUtil;
import com.owl.util.RandomUtil;

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

    /*
     * 判斷是否是第一次鏈接
     * @param socketChannel
     * @return
     */
    private SocketMsg addToSocketClientSet(AsynchronousSocketChannel socketChannel) {
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

    public void dispatchEvent(AsynchronousSocketChannel socketChannel, Map<String, String> msg) {
        SocketMsg model = addToSocketClientSet(socketChannel);
        if (null != msg && null != msg.get("event")) {
            model.setEvent(new SocketEvent(msg));
        }
        ConsolePrintUtil.info("dispatch event success. msg is " + ObjectUtil.toJSON(model));
    }

    public void dispatchEvent(AsynchronousSocketChannel socketChannel, SocketEvent event) {
        SocketMsg model = addToSocketClientSet(socketChannel);
        model.setEvent(event);
        ConsolePrintUtil.info("dispatch event success. msg is " + ObjectUtil.toJSON(model));
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
