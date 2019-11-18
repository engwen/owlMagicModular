package com.owl.util;


import com.sun.prism.paint.Color;

import java.util.Date;


/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/6/25.
 */
public abstract class LogPrintUtil {

    public static void info(String msg) {
        log("\033[0;35m", msg, "INFO");
    }

    public static void info(Object msg) {
        log("\033[0;35m", ObjectUtil.toJSON(msg), "INFO");
    }

    public static void error(String msg) {
        log("\033[0;31m", msg, "ERROR");
    }

    private static void log(String code, String msg, String level) {
        System.out.println(code + "[" + level + "][" + DateCountUtil.getDateFormSdf(new Date(), DateCountUtil.YMDHMS4BAR) +
                "][" + Thread.currentThread().getStackTrace()[3].getClassName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "] " + msg);
    }
}
