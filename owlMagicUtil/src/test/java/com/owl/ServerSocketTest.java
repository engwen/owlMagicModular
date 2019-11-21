package com.owl;

import com.owl.io.socket.server.SocketServerService;
import org.junit.Test;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/11/13.
 */
public class ServerSocketTest {

    @Test
    public void start() throws InterruptedException {
        SocketServerService socketServerService = new SocketServerService(8092);
        Thread.sleep(1000000);
    }
}
