package com.owl.socket.server;

import com.owl.socket.server.handler.AcceptCompleteHandler;
import com.owl.socket.model.SocketClient;
import com.owl.socket.model.SocketRoom;
import com.owl.util.LogPrintUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/11/13.
 */
public class SocketServerService {
    //記錄服務器channel
    private AsynchronousServerSocketChannel serverSocketChannel;
    //group
    private AsynchronousChannelGroup channelGroup;
    //port
    private int port;

    //this channel have room list
    private List<SocketRoom> socketRoomList;
    //this channel have client list
    private Set<SocketClient> socketClientSet;

    public SocketServerService(int port) {
        socketRoomList = new ArrayList<>();
        socketClientSet = new HashSet<>();
        this.port = port;
        try {
            init();
        } catch (IOException e) {
            LogPrintUtil.error("start socket error, information is " + e);
        }
    }

    private void init() throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        channelGroup = AsynchronousChannelGroup.withThreadPool(executorService);
        serverSocketChannel = AsynchronousServerSocketChannel.open(channelGroup).bind(new InetSocketAddress(this.port));
//      使用监听器来接收来自客户端的链接请求
        serverSocketChannel.accept(null, new AcceptCompleteHandler(serverSocketChannel));
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

    public boolean stop() {
        try {
            channelGroup.shutdown();
            serverSocketChannel.close();
            socketRoomList = null;
            socketClientSet = null;
        } catch (IOException e) {
            LogPrintUtil.error("stop socket error, information is " + e);
            return false;
        }
        return true;
    }

}


