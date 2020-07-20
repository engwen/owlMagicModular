package com.owl.mvc.utils;

import com.owl.mvc.model.MsgConstant;
import com.owl.util.LogPrintUtil;
import com.owl.util.PropertiesUtil;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2020/7/4.
 */
public class PropertiesJarUtil {

    private static final String CONFIG = "config";

    /**
     * 讀取指定配置文件信息
     * @param key key
     * @return str
     */
    public static String readProperties(String fileName, String key) {
        try {
            Properties properties = PropertiesLoaderUtils.loadAllProperties(fileName + ".properties");
            return properties.getProperty(key);
        } catch (Exception e) {
            LogPrintUtil.error(MsgConstant.NOT_FIND_PROPERTIES.getMsg());
        }
        return null;
    }

    /**
     * 讀取默認配置文件信息
     * @param key key
     * @return str
     */
    public static String readConfigProperties(String key) {
        return readProperties(CONFIG, key);
    }
}
