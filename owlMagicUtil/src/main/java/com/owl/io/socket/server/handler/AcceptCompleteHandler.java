package com.owl.io.socket.server.handler;

import com.owl.io.socket.model.SocketEvent;
import com.owl.io.socket.server.SocketDispatch;
import com.owl.io.socket.server.SocketServer;
import com.owl.util.ConsolePrintUtil;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/11/12.
 */
public class AcceptCompleteHandler implements CompletionHandler<AsynchronousSocketChannel, SocketServer> {
    private AsynchronousServerSocketChannel serverSocketChannel;
    private SocketDispatch dispatch;

    public AcceptCompleteHandler(AsynchronousServerSocketChannel serverSocketChannel, SocketDispatch dispatch) {
        this.serverSocketChannel = serverSocketChannel;
        this.dispatch = dispatch;
    }

    /**
     * Invoked when an operation has completed.
     * @param result     The result of the I/O operation.
     * @param attachment OwlSocketServer
     */
    @Override
    public void completed(AsynchronousSocketChannel result, SocketServer attachment) {
        // 继续接收其他的客户端连接
        serverSocketChannel.accept(null, this);
        ConsolePrintUtil.info("connect success");
        dispatch.dispatchEvent(result, SocketEvent.SERVER_CONNECT_SUCCESS);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //关闭处理完的socket，并重新调用accept等待新的连接
        result.read(byteBuffer, byteBuffer, new ReadCompleteHandler(result, dispatch));
    }

    /**
     * Invoked when an operation fails.
     * @param exc        The exception to indicate why the I/O operation failed
     * @param attachment OwlSocketServer
     */
    @Override
    public void failed(Throwable exc, SocketServer attachment) {
        ConsolePrintUtil.error("connect error");
    }
}