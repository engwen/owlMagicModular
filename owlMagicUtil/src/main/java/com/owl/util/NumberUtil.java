package com.owl.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2020/5/14.
 */
public abstract class NumberUtil {

    /**
     * 不带千分位
     * 数字转 String，避免显示科学计数法,范围参考 BigDecimal
     * @param value 值
     * @param scale 小数长度
     * @return 字符串
     */
    public static String numberToStr(Double value, int scale) {
        return new BigDecimal(value).setScale(scale, RoundingMode.HALF_UP).toEngineeringString();
    }

    /**
     * 带千分位
     * 数字转 String，避免显示科学计数法,范围参考 BigDecimal
     * @param value 值
     * @param scale 小数长度
     * @return 字符串
     */
    public static String numberToThousandStr(Double value, int scale) {
        String s = new BigDecimal(value).setScale(scale, RoundingMode.HALF_UP).toEngineeringString();
        if (value > 0.0) {
            String[] split = s.split("\\.");
            StringBuilder result = new StringBuilder();
            char[] chars = split[0].toCharArray();
            for (int len = chars.length, i = 0; i < len; i++) {
                if (i != 0 && i % 3 == 0) result.append(",");
                result.append(chars[len - i - 1]);
            }
            return result.reverse().toString() + (split.length > 1 ? ("." + split[1]) : "");
        } else {
            return s;
        }
    }

    /**
     * 将指定数据按照指定长度补零返回成字符
     * @param value 值
     * @param len   长度
     * @return 字符串
     */
    public static String numberToStr(Integer value, int len) {
        char[] chars = value.toString().toCharArray();
        int oldLen = chars.length;
        if (oldLen < len) {
            char[] newChars = new char[len];
            for (int i = 0, j = len - oldLen; i < j; i++) {
                newChars[i] = '0';
            }
            System.arraycopy(chars, 0, newChars, len - oldLen, oldLen);
            return new String(newChars);
        }
        return value.toString();
    }
}
