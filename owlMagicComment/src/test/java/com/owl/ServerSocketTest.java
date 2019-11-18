package com.owl;

import com.owl.socket.SocketServer;
import org.junit.Test;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/11/13.
 */
public class ServerSocketTest {

    @Test
    public void start() throws InterruptedException {
        SocketServer socketServer = new SocketServer(8092);
        Thread.sleep(1000000);
    }
}
