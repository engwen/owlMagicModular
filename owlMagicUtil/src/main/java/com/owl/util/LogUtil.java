package com.owl.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/6/25.
 */
public abstract class LogUtil {

    private static final Logger logger = LoggerFactory.getLogger(LogUtil.class);

    public static void info(String msg) {
        logger.info(msg);
    }

    public static void warn(String msg) {
        logger.warn(msg);
    }

    public static void error(String msg) {
        logger.error(msg);
    }
}
