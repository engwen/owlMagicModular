package com.owl.util;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2020/6/13.
 */
public abstract class ShellUtil {

    public static void start(String command) {
        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec(command);
        } catch (Exception e) {
            if (null != proc) {
                proc.destroy();
            }
            ConsolePrintUtil.error(e.toString());
        } finally {

        }
    }
}
