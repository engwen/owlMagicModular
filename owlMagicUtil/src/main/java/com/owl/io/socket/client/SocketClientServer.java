package com.owl.io.socket.client;

import com.owl.io.socket.model.SocketMsg;
import com.owl.util.LogUtil;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/11/13.
 */
public class SocketClientServer {
    private String host;
    private int port;
    private String uuid;
    private AsynchronousSocketChannel clientChannel;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    private SocketClientServer(String host, int port) {
        this.host = host;
        this.port = port;
        this.connect();
    }

    public static SocketClientServer getInstance(String host, int port) {
        return new SocketClientServer(host, port);
    }

    private void connect() {
        try {
            // 创建一个线程池
            ExecutorService executor = Executors.newFixedThreadPool(80);
            // 以指定线程池来创建一个AsynchronousChannelGroup
            AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup.withThreadPool(executor);
            // 以channelGroup作为组管理器来创建AsynchronousSocketChannel
            clientChannel = AsynchronousSocketChannel.open(channelGroup);
            // 让AsynchronousSocketChannel连接到指定IP、指定端口
            clientChannel.connect(new InetSocketAddress(this.host, this.port));
//        socketChannel.finishConnect();
        } catch (Exception e) {
            LogUtil.error("connect error, information is  " + e);
        }
    }

    public void disconnect() {
        try {
            this.clientChannel.close();
        } catch (Exception e) {
            LogUtil.error("disconnect error, information is  " + e);
        }
    }

    public void emit(SocketMsg msg) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(msg.toString().getBytes());
        buffer.flip();
        try {
            this.clientChannel.write(buffer).get();
            buffer.flip();
            buffer.clear();
            this.clientChannel.read(buffer).get();
            buffer.flip();
            LogUtil.info("get server back  " + new String(buffer.array()).trim());
        } catch (Exception e) {
            LogUtil.error("emit msg is Error :" + e);
        }
    }

    public void on() {

    }
}
