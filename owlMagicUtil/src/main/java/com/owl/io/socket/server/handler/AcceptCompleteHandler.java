package com.owl.io.socket.server.handler;

import com.owl.io.socket.model.SocketMsg;
import com.owl.io.socket.server.SocketDispatch;
import com.owl.io.socket.server.SocketServer;
import com.owl.io.socket.util.SocketUtils;
import com.owl.util.LogUtil;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.ArrayList;
import java.util.List;

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
        LogUtil.info("connect success");
        SocketMsg backMsg = SocketMsg.getInstance();
        List<String> accepterIds = new ArrayList<>();
        accepterIds.add(SocketUtils.getUuid(result));
        backMsg.setMsgType(0);
        backMsg.setNeedBack(false);
        backMsg.setSenderId("000000000001");
        backMsg.setAccepterIds(accepterIds);
        backMsg.getMsg().put("to:client:type", "success");
        dispatch.dispatchEvent(result, backMsg);
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
        LogUtil.error("connect error");
    }
}