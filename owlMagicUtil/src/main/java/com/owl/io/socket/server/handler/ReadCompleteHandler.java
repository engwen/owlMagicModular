package com.owl.io.socket.server.handler;

import com.owl.io.socket.model.SocketEvent;
import com.owl.io.socket.server.SocketDispatch;
import com.owl.util.LogPrintUtil;
import com.owl.util.ObjectUtil;
import com.owl.util.RegexUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Map;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/11/14.
 */
public class ReadCompleteHandler implements CompletionHandler<Integer, ByteBuffer> {
    private AsynchronousSocketChannel socketChannel;
    private SocketDispatch dispatch;

    public ReadCompleteHandler(AsynchronousSocketChannel socketChannel, SocketDispatch dispatch) {
        this.socketChannel = socketChannel;
        this.dispatch = dispatch;
    }

    /**
     * Invoked when an operation has completed.
     * @param result     The result of the I/O operation.
     * @param attachment
     */
    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        String msg = new String(attachment.array());
        if (!RegexUtil.isEmpty(msg)) {
            Map<String, Object> resultMsg = ObjectUtil.StringToMap(new String(attachment.array()));
            dispatch.dispatchEvent(socketChannel, resultMsg);
        } else {
            return;
        }
        LogPrintUtil.info("read success. msg is " + msg);
        attachment.clear();
        attachment.put(ObjectUtil.toJSON(SocketEvent.SERVER_REQUEST_SUCCESS).getBytes());
        attachment.flip();
        socketChannel.write(attachment);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        socketChannel.read(byteBuffer, byteBuffer, this);
    }

    /**
     * Invoked when an operation fails.
     * @param exc        The exception to indicate why the I/O operation failed
     * @param attachment
     */
    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        LogPrintUtil.error("read error. Information is " + exc);
        try {
            LogPrintUtil.info("close socket success. " + socketChannel.getRemoteAddress());
            socketChannel.close();
        } catch (IOException e) {
            LogPrintUtil.error("close socket error. Information is " + e);
            e.printStackTrace();
        }
    }
}
