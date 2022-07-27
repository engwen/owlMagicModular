package com.owl.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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
            InputStreamReader inputStreamReader = new InputStreamReader(proc.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            System.out.println(bufferedReader);
        } catch (Exception e) {
            if (null != proc) {
                proc.destroy();
            }
            ConsolePrintUtil.error(e.toString());
        } finally {

        }
    }
}
