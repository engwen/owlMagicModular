package com.owl.io;

import com.owl.util.LogPrintUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/11/13.
 */
public class SocketServer implements Runnable {

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private int port;

    private boolean isRun = true;

    public void stop() {
        this.isRun = false;
    }

    public SocketServer(int port) {
        this.port = port;
        try {
            init();
        } catch (IOException e) {
            LogPrintUtil.error("start socket error, information is " + e);
        }
    }

    public void on() {

    }

    private void init() throws IOException {
        //socketServer
        serverSocketChannel = ServerSocketChannel.open();
        //build port
        serverSocketChannel.socket().bind(new InetSocketAddress(this.port));
        selector = Selector.open();
        //非阻塞
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     * @see Thread#run()
     */
    @Override
    public void run() {
        try {
            while (isRun) {
                if (selector.select() == 0) {
                    continue;
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.write(ByteBuffer.wrap("connect success ".getBytes()));
                        socketChannel.register(selector, SelectionKey.OP_READ);

                    } else if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        buffer.clear();
                        //从channel读数据到缓冲区
                        int readBytes = channel.read(buffer);
                        if (readBytes > 0) {
                            String msg = new String(buffer.array(), StandardCharsets.UTF_8).trim();
                            System.out.println("server receive from client: " + msg);
                            ByteBuffer outBuffer = ByteBuffer.wrap("server get this ".getBytes());
                            channel.write(outBuffer);
                            channel.close();
                        } else {
                            channel.close();
                        }
                    } else if (key.isWritable()) {
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        byte[] data = buffer.array();
                        String msg = new String(data).trim();
                        System.out.println("server writable from client: " + msg);
                    } else if (key.isValid()) {

                    }
                    iterator.remove();
                }
            }
        } catch (Exception e) {
        }
    }
}
