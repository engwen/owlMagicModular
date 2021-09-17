package com.owl.io.socket.server.handler;

import com.owl.io.socket.model.SocketMsg;
import com.owl.io.socket.server.SocketDispatch;
import com.owl.io.socket.util.SocketUtils;
import com.owl.util.ConsolePrintUtil;
import com.owl.util.ObjectUtil;
import com.owl.util.RegexUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
     * @param attachment ByteBuffer
     */
    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        String msg = new String(attachment.array());
        if (!RegexUtil.isEmpty(msg)) {
            Map<String, Object> resultMsg = ObjectUtil.StringToMap(new String(attachment.array()));
            if (null == resultMsg) {
                return;
            }
            Map<String, String> resultM = new HashMap<>();

            resultMsg.forEach((key, value) -> {
                resultM.put(key, value.toString());
            });
//            dispatch.dispatchEvent(socketChannel, resultM);
        } else {
            return;
        }
        ConsolePrintUtil.info("read success. msg is " + msg);
        attachment.clear();
        SocketMsg backMsg = SocketMsg.getInstance();
        List<String> accepterIds = new ArrayList<>();
        accepterIds.add(SocketUtils.getUuid(socketChannel));
        backMsg.setMsgType(0);
        backMsg.setNeedBack(false);
        backMsg.setSenderId("000000000001");
        backMsg.setAccepterIds(accepterIds);
        backMsg.getMsg().put("to:client:type", "success");
        attachment.put(backMsg.toString().getBytes(StandardCharsets.UTF_8));
        ConsolePrintUtil.info("return msg " + backMsg);
        attachment.flip();
        socketChannel.write(attachment);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        socketChannel.read(byteBuffer, byteBuffer, this);
    }

    /**
     * Invoked when an operation fails.
     * @param exc        The exception to indicate why the I/O operation failed
     * @param attachment ByteBuffer
     */
    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        ConsolePrintUtil.error("read error. Information is " + exc);
        try {
            ConsolePrintUtil.info("close socket success. " + socketChannel.getRemoteAddress());
            socketChannel.close();
        } catch (IOException e) {
            ConsolePrintUtil.error("close socket error. Information is " + e);
            e.printStackTrace();
        }
    }
}
