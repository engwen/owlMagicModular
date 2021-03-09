package com.owl.main;

import com.owl.util.ConsolePrintUtil;
import com.owl.util.FileUtil;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2021/1/20.
 */
public class CheckRepeatFile {
    public static void main(String[] args) {
        if (args.length == 1) {
            FileUtil.getRepeatFileByPath(args[0]);
        } else {
            ConsolePrintUtil.error("参数错误");
        }
    }
}
