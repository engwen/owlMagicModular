package com.owl.io.socket.server;

import com.owl.io.socket.model.SocketMsg;

import java.nio.channels.AsynchronousSocketChannel;

/**
 * 發送訂閲的消息
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/12/5.
 */
public interface SocketDispatch {
    void dispatchEvent(AsynchronousSocketChannel dispatcher, SocketMsg socketMsg);

    void clear();

}
