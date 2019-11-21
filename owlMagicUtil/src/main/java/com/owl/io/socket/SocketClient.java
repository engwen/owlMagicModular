package com.owl.io.socket;

import com.owl.model.ModelPrototype;

import java.nio.channels.AsynchronousSocketChannel;

/**
 * socket 客戶端鏈接信息
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/11/18.
 */
public class SocketClient extends ModelPrototype {
    private String uuid;
    private AsynchronousSocketChannel socketChannel;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public AsynchronousSocketChannel getSocketChannel() {
        return socketChannel;
    }

    public void setSocketChannel(AsynchronousSocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }
}
