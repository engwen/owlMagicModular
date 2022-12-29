package com.owl.main;

import com.owl.util.MD5Util;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2021/1/20.
 */
public class GetMD5 {
    public static void main(String[] args) {
        if (args.length == 1) {
            System.out.println(MD5Util.getMD5(args[0]));
        } else {
            System.out.println("参数错误");
        }
    }
}
