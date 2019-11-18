package com.owl.util;

import java.io.*;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * 讀取配置文件工具類（根目錄下）
 * author engwen
 * email xiachanzou@outlook.com
 * 2017/4/7.
 */
public abstract class PropertiesUtil {
    private static final String CONFIG = "config";

    private static final String CONFIG_PROPERTIES = "config.properties";
    private static String DEFAULT_PATH = (System.getProperty("user.dir") + File.separatorChar + "WEB-INF" + File.separatorChar + "classes" + File.separatorChar);

    /**
     * 讀取指定配置文件信息
     * @param propertiesName 屬性名稱
     * @param key            key
     * @return str
     */
    public static String readProperties(String propertiesName, String key) {
        return ResourceBundle.getBundle(propertiesName, Locale.getDefault()).getString(key);
    }

    /**
     * 讀取默認配置文件信息
     * @param key key
     * @return str
     */
    public static String readConfigProperties(String key) {
        return readProperties(CONFIG, key);
    }


    /**
     * 向指定文件写入新的属性
     * @param propertiesName 文件名
     * @param key            键
     * @param nameHasPath    名称中是否包含位置信息
     * @param value          值
     * @throws Exception ex
     */
    public static void writeProperties(String propertiesName, boolean nameHasPath, String key, String value) throws Exception {
        Properties properties = new Properties();
        String filePath = nameHasPath ? propertiesName : (DEFAULT_PATH + propertiesName);
        InputStream in = new FileInputStream(filePath);
        properties.load(in);
        OutputStream out = new FileOutputStream(filePath);
        properties.setProperty(key, value);
        properties.store(out, new Date() + "添加新属性");
    }

    /**
     * 想默认配置文件中写入属性
     * @param key   键
     * @param value 值
     * @throws Exception ex
     */
    public static void writeConfigProperties(String key, String value) throws Exception {
        writeProperties(CONFIG_PROPERTIES, false, key, value);
    }
}
