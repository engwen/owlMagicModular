package com.owl.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;

/**
 * 读取jar中的文件信息
 * author engwen
 * email xiachanzou@outlook.com
 * 2021/4/1.
 */
public abstract class JarFileUtil {
    public static String filePath(String path) {
        URL resource = JarFileUtil.class.getClassLoader().getResource(path);
        if (null != resource) {
            return resource.getPath();
        }
        return null;
    }

    public static InputStream fileInputStream(String path) {
        return JarFileUtil.class.getClassLoader().getResourceAsStream(path);
    }

    /**
     * 拷贝指定文件夹下的指定名称的文件
     * @param sourceDir 源文件所在文件夹
     * @param targetDir 目标目录
     * @param names     文件名集合
     * @throws IOException io异常
     */
    public void copyFile(String sourceDir, String targetDir, String[] names) throws IOException {
        File dir = new File(targetDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        for (String fileName : names) {
            File temp = new File(targetDir + File.separatorChar + fileName);
            InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(sourceDir + fileName);
            if (null != resourceAsStream) {
                Files.copy(resourceAsStream, temp.toPath());
            }
        }
        ConsolePrintUtil.info(sourceDir + "：目录下文件拷贝完成");
    }
}
