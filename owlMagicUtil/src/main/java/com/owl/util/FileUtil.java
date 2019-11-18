package com.owl.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件處理信息
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/5/23.
 */
public class FileUtil {
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
}
