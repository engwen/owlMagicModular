package com.owl.main;

import com.owl.util.FileUtil;
import com.owl.util.LogUtil;

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
            LogUtil.error("参数错误");
        }
    }
}
