package com.owl.util;

import com.owl.io.file.FileIO;

import java.io.*;
import java.util.*;

/**
 * 文件處理信息
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/5/23.
 */
public class FileUtil {

    /**
     * 创建目录
     * @param dir 文件夹路径
     * @return 是否成功
     */
    public static boolean createDirectory(String dir) {
        File dirFile = new File(dir);
        return dirFile.mkdirs();
    }

    /**
     * 写入文件
     * @param dir      文件目录(不带文件名)
     * @param fileName 文件名(带后缀)
     * @param content  写入内容
     */
    public static void writeFile(String dir, String fileName, String content) {
        FileIO.writeFile(dir, fileName, content);
    }

    /**
     * 读取文件
     * @param filePath 文件路径,带文件名后缀
     * @return 文件内容
     */
    public static String readFile(String filePath) {
        return FileIO.readFile(filePath);
    }

    /**
     * 写入StringList
     * @param list 文件内容
     * @param path 路径
     */
    public static void writerListToFile(List<String> list, String path) {
        try {
            FileWriter writer = new FileWriter(path);
            for (String str : list) {
                writer.write(str);
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 追加写入
     * @param str  追加内容
     * @param path 路径
     */
    public static void writerLastToFile(String str, String path) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path, true));
            bufferedWriter.newLine();
            bufferedWriter.append(str);
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件内容,按list返回
     * @param path 文件卢姐
     * @return 内容
     */
    public static List<String> readListFromFile(String path) {
        List<String> list = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line = bufferedReader.readLine();
            while (!RegexUtil.isEmpty(line)) {
                list.add(line);
                line = bufferedReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 判断文件是否重复
     * @param filePath1 文件1
     * @param filePath2 文件2
     * @return 是否重复
     */
    public static boolean checkFileIsRepeat(String filePath1, String filePath2) {
        File file1 = new File(filePath1);
        File file2 = new File(filePath2);
        if (file1.isDirectory() || file2.isDirectory()) {
            LogPrintUtil.error("此方法不支持目录比较,请使用 getRepeatFileByPath");
        } else if (file1.exists() && file2.exists()) {
            String file1md5 = MD5Util.getMD5(file1);
            String file2md5 = MD5Util.getMD5(file2);
            return file1md5.equals(file2md5);
        }
        return false;
    }

    /**
     * 获取重复的文件
     * @param dirPath 文件地址
     * @return 重复信息
     */
    public static Map<String, List<File>> getRepeatFileByPath(String dirPath) {
        Map<String, List<File>> repeatFile = new HashMap<>();
        Map<String, List<File>> fileMap = new HashMap<>();
        File dir = new File(dirPath);
        getFilePathMd5(fileMap, dir);
        fileMap.keySet().stream().filter(key -> fileMap.get(key).size() != 1).forEach(key -> {
            repeatFile.put(key, fileMap.get(key));
            LogPrintUtil.info(String.format("MD5 %s 路径文件存在重复", key));
            fileMap.get(key).forEach(it -> LogPrintUtil.info(it.getAbsolutePath()));
        });
        if (repeatFile.keySet().size() == 0) {
            LogPrintUtil.info("该目录下文件不存在重复");
        }
        return repeatFile;
    }

    /**
     * 读取指定目录下的全部文件,返回MD5+文件list
     * @param fileMap MD5+文件list
     * @param file    文件或目录
     */
    private static void getFilePathMd5(Map<String, List<File>> fileMap, File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File oneFile : files) {
                    getFilePathMd5(fileMap, oneFile);
                }
            }
        } else {
            String key = MD5Util.getMD5(file);
            Optional<String> first = fileMap.keySet().stream().filter(it -> it.equals(key)).findFirst();
            if (!first.isPresent()) {
                List<File> fileList = new ArrayList<>();
                fileMap.put(key, fileList);
            }
            fileMap.get(key).add(file);
        }
    }
}
