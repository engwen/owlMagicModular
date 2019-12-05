package com.owl.io.socket.server;

import com.owl.io.socket.SocketDispatchEvent;
import com.owl.io.socket.server.handler.AcceptCompleteHandler;
import com.owl.util.LogPrintUtil;
import com.owl.util.RegexUtil;

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
public class OwlSocketServer {
    //記錄服務器channel
    private AsynchronousServerSocketChannel serverSocketChannel;
    //group
    private AsynchronousChannelGroup channelGroup;
    //port
    private int port;
    //dispatch
    private SocketDispatch dispatch;


    public static OwlSocketServer getInstance(int port, SocketDispatch dispatch) {
        if (RegexUtil.isEmpty(dispatch)) {
            LogPrintUtil.error("start socket error,dispatch can`t be null ");
            return null;
        }
        return new OwlSocketServer(port, dispatch);
    }

    private OwlSocketServer(int port, SocketDispatch dispatch) {
        this.port = port;
        this.dispatch = dispatch;
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
        serverSocketChannel.accept(null, new AcceptCompleteHandler(serverSocketChannel,dispatch));
    }

    public boolean stop() {
        try {
            channelGroup.shutdown();
            serverSocketChannel.close();
            SocketDispatchEvent.clear();
        } catch (IOException e) {
            LogPrintUtil.error("stop socket error, information is " + e);
            return false;
        }
        return true;
    }

}


