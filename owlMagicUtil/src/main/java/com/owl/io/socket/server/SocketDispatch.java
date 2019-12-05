package com.owl.io.socket.server;

import com.owl.io.socket.model.SocketEvent;

import java.nio.channels.AsynchronousSocketChannel;
import java.util.Map;

/**
 * 發送訂閲的消息
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/12/5.
 */
public interface SocketDispatch {
    void dispatchEvent(AsynchronousSocketChannel dispatcher, Map<String, Object> msg);

    void dispatchEvent(AsynchronousSocketChannel dispatcher, SocketEvent msg);
}
