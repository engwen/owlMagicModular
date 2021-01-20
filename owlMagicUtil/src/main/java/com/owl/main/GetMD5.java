package com.owl.main;

import com.owl.util.LogPrintUtil;
import com.owl.util.MD5Util;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2021/1/20.
 */
public class GetMD5 {
    public static void main(String[] args) {
        if (args.length == 1) {
            LogPrintUtil.info(MD5Util.getMD5(args[0]));
        } else {
            LogPrintUtil.error("参数错误");
        }
    }
}
