package com.owl;

import org.junit.Test;

import java.io.IOException;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/11/13.
 */
public class ClientSocket {
    @Test
    public void oepns() throws IOException, InterruptedException {
        SocketClient client = new SocketClient("localhost",8092);
//        SocketClient client2 = new SocketClient("localhost",8092);
        client.emit("testEvent","aaaaaaaaaaa");


//        client2.emit("testEvent","bbbbbbbbbbbbb");
        client.emit("testEvent","dddddddd");
        client.disconnect();
//        Thread.sleep(10000);
//        client2.disconnect();




//        while (true) {
//
//            if (selector.select() == 0) {
//                continue;
//            }
//            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
//            while (iterator.hasNext()) {
//                SelectionKey key = iterator.next();
//
//                if (key.isConnectable()) {
//                    SocketChannel channel = (SocketChannel) key.channel(); // 如果正在连接，则完成连接
//                    if (channel.isConnectionPending()) {
//                        channel.finishConnect();
//                    } // 设置成非阻塞
//                    channel.configureBlocking(false);
//                    channel.write(ByteBuffer.wrap(new String("hello server!").getBytes()));
//                    channel.register(selector, SelectionKey.OP_READ); // 获得了可读的事件
//                } else if (key.isReadable()) {
//                    ByteBuffer buffer = (ByteBuffer) key.attachment();
//                    byte[] data = buffer.array();
//                    String msg = new String(data).trim();
//                    System.out.println("client receive from server: " + msg);
//                } else if (key.isWritable()) {
//                    ByteBuffer buffer = (ByteBuffer) key.attachment();
//                    byte[] data = buffer.array();
//                    String msg = new String(data).trim();
//                    System.out.println("client receive from server: " + msg);
//                }
//            }
//        }
    }
}
