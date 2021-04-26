package com.owl.util;

import com.owl.util.model.OwlLogFormatter;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 日志工具
 * author engwen
 * email xiachanzou@outlook.com
 * 2021/4/21.
 */
public abstract class LoggerUtil {
    private static final Map<String, Logger> lgMap = new HashMap<>();
    private static FileHandler fileHandler = null;

    public static FileHandler getFileHandler() {
        return fileHandler;
    }

    public static void setFileHandler(FileHandler fileHandler) {
        LoggerUtil.fileHandler = fileHandler;
    }

    public static void info(String msg) {
        log(msg, Level.INFO);
    }

    public static void warning(String msg) {
        log(msg, Level.WARNING);
    }

    public static void error(String msg) {
        log(msg, Level.SEVERE);
    }

    private static void log(String msg, Level level) {
        String className = Thread.currentThread().getStackTrace()[3].getClassName();
        Logger logger = lgMap.get(className);
        if (null == logger) {
            logger = Logger.getLogger(className);
            lgMap.put(className, logger);
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new OwlLogFormatter());
            if (null != fileHandler) {
                logger.addHandler(fileHandler);
            }
            logger.addHandler(consoleHandler);
        }
        logger.log(level, msg);
    }
}
