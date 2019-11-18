package com.owl.socket;

import com.owl.util.LogPrintUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/11/13.
 */
public class SocketServer {
    private AsynchronousServerSocketChannel serverSocketChannel;
    private AsynchronousChannelGroup channelGroup;
    private int port;


    public boolean stop() {
        try {
            channelGroup.shutdown();
            serverSocketChannel.close();
        } catch (IOException e) {
            LogPrintUtil.error("stop socket error, information is " + e);
            return false;
        }
        return true;
    }

    public SocketServer(int port) {
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
}


