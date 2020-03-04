package com.owl.util;

import java.math.BigDecimal;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2020/3/4.
 */
public class NumberUtil {

    /**
     * 不带千分位
     * 数字转 String，避免显示科学计数法,范围参考 BigDecimal
     * @param value 值
     * @param scale 小数长度
     * @return
     */
    public static String numberToStr(Double value, int scale) {
        return new BigDecimal(value).setScale(scale, BigDecimal.ROUND_HALF_UP).toEngineeringString();
    }

    /**
     * 带千分位
     * 数字转 String，避免显示科学计数法,范围参考 BigDecimal
     * @param value 值
     * @param scale 小数长度
     * @return
     */
    public static String numberToThousandStr(Double value, int scale) {
        String s = new BigDecimal(value).setScale(scale, BigDecimal.ROUND_HALF_UP).toEngineeringString();
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
}
