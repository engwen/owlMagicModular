package com.owl.factory;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 綫程池
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/7/4.
 */
public class OwlThreadPool {
    private static ExecutorService threadPool = Executors.newCachedThreadPool();

    public static ExecutorService getThreadPool() {
        return threadPool;
    }
}
