package com.owl.io.socket.util;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2021/9/17.
 */
public class SocketUtils {

    public static String getUuid(AsynchronousSocketChannel socketChannel) {
        try {
            return socketChannel.getRemoteAddress().toString().replace("/", "")
                    .replace(".", "").replace(":", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
