package com.owl.io.socket.server.handler;

import com.owl.io.socket.server.SocketServerService;
import com.owl.util.LogPrintUtil;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/11/12.
 */
public class AcceptCompleteHandler implements CompletionHandler<AsynchronousSocketChannel, SocketServerService> {
    private AsynchronousServerSocketChannel serverSocketChannel;

    public AcceptCompleteHandler(AsynchronousServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;
    }

    /**
     * Invoked when an operation has completed.
     *
     * @param result     The result of the I/O operation.
     * @param attachment
     */
    @Override
    public void completed(AsynchronousSocketChannel result, SocketServerService attachment) {
        // 继续接收其他的客户端连接
        serverSocketChannel.accept(null, this);
        LogPrintUtil.info("connect success");
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //关闭处理完的socket，并重新调用accept等待新的连接
        result.read(byteBuffer, byteBuffer, new ReadCompleteHandler(result));
    }

    /**
     * Invoked when an operation fails.
     *
     * @param exc        The exception to indicate why the I/O operation failed
     * @param attachment
     */
    @Override
    public void failed(Throwable exc, SocketServerService attachment) {
        LogPrintUtil.error("connect error");
    }
}