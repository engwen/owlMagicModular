package com.owl.socket.model;

import com.owl.model.ModelPrototype;

import java.nio.channels.AsynchronousSocketChannel;

/**
 * socket 客戶端鏈接信息
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/11/18.
 */
public class SocketClient extends ModelPrototype {
    private Integer uuid;
    private AsynchronousSocketChannel socketChannel;

    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public AsynchronousSocketChannel getSocketChannel() {
        return socketChannel;
    }

    public void setSocketChannel(AsynchronousSocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }
}
