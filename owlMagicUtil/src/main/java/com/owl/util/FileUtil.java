package com.owl.util;

import com.owl.io.file.FileIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件處理信息
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/5/23.
 */
public abstract class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 创建目录
     * @param dir 文件夹路径
     */
    public static void createDirectory(String dir) {
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            try {
                logger.info("创建文件夹：" + dirFile);
                Files.createDirectory(dirFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logger.info("目录已存在：" + dirFile);
        }
    }

    /**
     * 创建目录
     * @param dir 文件夹路径
     */
    public static void createDirectories(String dir) {
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            try {
                logger.info("创建文件夹：" + dirFile);
                Files.createDirectories(dirFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logger.info("目录已存在：" + dirFile);
        }
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
            logger.error("此方法不支持目录比较,请使用 getRepeatFileByPath");
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
        FileUtil.getFilePathMd5(fileMap, dir);
        fileMap.keySet().stream().filter(key -> fileMap.get(key).size() != 1).forEach(key -> {
            repeatFile.put(key, fileMap.get(key));
            logger.info(String.format("MD5 %s 路径文件存在重复", key));
            fileMap.get(key).forEach(it -> logger.info(it.getAbsolutePath()));
        });
        if (repeatFile.keySet().size() == 0) {
            logger.info("该目录下文件不存在重复");
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
                    FileUtil.getFilePathMd5(fileMap, oneFile);
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

    /**
     * 获取指定文件的绝对路径，支持文件夹
     * @param file 文件
     * @return 文件集合
     */
    public static List<File> getFilePath(File file) {
        List<File> fileList = new ArrayList<>();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File oneFile : files) {
                    fileList.addAll(FileUtil.getFilePath(oneFile));
                }
            }
        } else {
            fileList.add(file);
        }
        return fileList;
    }

    /**
     * 拷贝源文件到指定目录，并
     * @param sourceDir         源文件位置初级路径
     * @param targetDir         目标位置初级路径
     * @param needMkdirFilePath 需要开始拷贝的路径+文件名
     */
    public static void copyFile(String sourceDir, String targetDir, String needMkdirFilePath) {
        //目标位置初级路径
        String sourceFilePath = sourceDir + File.separatorChar + needMkdirFilePath;
        String tagPath = targetDir + File.separatorChar + needMkdirFilePath;
        File file = new File(tagPath);
        File parentFile = file.getParentFile();
        try {
            if (!parentFile.exists()) {
                Files.createDirectories(parentFile.toPath());
            }
            Files.copy(new File(sourceFilePath).toPath(), file.toPath());
        } catch (IOException e) {
            logger.error("拷贝失败：" + sourceFilePath + "=>" + tagPath);
            e.printStackTrace();
        }
    }


    /**
     * 拷贝指定文件夹下的指定名称的文件
     * @param sourceDir 源文件所在文件夹
     * @param targetDir 目标目录
     * @param names     文件名集合
     * @throws IOException io异常
     */
    public static void copyFile(String sourceDir, String targetDir, List<String> names) throws IOException {
        FileUtil.createDirectories(targetDir);
        for (String fileName : names) {
            File temp = new File(targetDir + File.separatorChar + fileName);
            File source = new File(sourceDir + fileName);
            if (source.exists()) {
                InputStream resourceAsStream = new FileInputStream(source);
                Files.copy(resourceAsStream, temp.toPath());
            } else {
                logger.error("未找到源文件：" + source.getAbsolutePath());
            }
        }
        logger.info(sourceDir + "：目录下文件拷贝完成");
    }

    /**
     * 按照目录结构完整的拷贝目录下文件
     * @param sourceDir 源文件目录
     * @param targetDir 要拷贝到的目录
     * @param filter    文件名过滤器
     */
    public static void copyFileFormation(String sourceDir, String targetDir, String filter) {
        File dir = new File(sourceDir);
        List<File> filePath = getFilePath(dir);
        List<File> collect;
        if (null != filter && !filter.equals("")) {
            collect = filePath.stream().filter(it -> it.getName().contains(filter)).collect(Collectors.toList());
        } else {
            collect = filePath;
        }
        collect.forEach(it -> {
            String newFile = it.getAbsolutePath().replace(sourceDir, targetDir);
            File source = new File(newFile);
            logger.info(it.getAbsolutePath() + "=====>" + source.getAbsolutePath());
            try {
                File parentFile = source.getParentFile();
                if (!parentFile.exists()) {
                    Files.createDirectories(parentFile.toPath());
                }
                Files.copy(it.toPath(), source.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                logger.error("拷贝失败");
                e.printStackTrace();
            }
        });
    }


    /*------------------------压缩文件--------------------------*/

    /**
     * 传入list的文件路径，打包到指定的新生成ZIP文件中，如果已存在的Zip，将会被覆盖
     * <p>
     * 特注：zos一定放在循环外结束
     * @param zipFilename 名稱
     * @param path        路徑
     * @return boolean
     * @throws Exception ex
     */
    public static boolean createFileToZip(String zipFilename, List<String> path) throws Exception {
        //压缩文件名
        File objFile = new File(zipFilename);
        ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(objFile), 1024));
        InputStream is;//每写完一个文件关闭一下
        ZipEntry ze = null;
        for (String s : path) {
            if (null != s) {
                continue;
            }
            File sourceFile = new File(s);
            if (!sourceFile.exists()) {
                continue;
            }
            byte[] buf = new byte[1024];
            //创建一个ZipEntry，并设置Name和最后时间
            ze = new ZipEntry(sourceFile.getName());
            ze.setSize(sourceFile.length());
            ze.setTime(sourceFile.lastModified());
            //将ZipEntry加到zos中
            zos.putNextEntry(ze);
            is = new BufferedInputStream(new FileInputStream(sourceFile));
            int readLen = -1;
            while ((readLen = is.read(buf, 0, 1024)) != -1) {
                zos.write(buf, 0, readLen);
            }
            is.close();
        }
        zos.close();
        return true;
    }


    /**
     * 将存放在sourceFilePath目录下的源文件，打包成fileName名称的zip文件，并存放到zipFilePath路径下
     * @param sourceFilePath :待压缩的文件路径
     * @param zipFilePath    :压缩后存放路径
     * @param fileName       :压缩后文件的名称
     * @return boolean
     */
    public static boolean fileToZip(String sourceFilePath, String zipFilePath, String fileName) {
        boolean flag = false;
        File sourceFile = new File(sourceFilePath);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        ZipOutputStream zos = null;

        if (!sourceFile.exists()) {
            logger.error("待压缩的文件目录：" + sourceFilePath + "不存在.");
        } else {
            try {
                File zipFile = new File(zipFilePath + File.separator + fileName + ".zip");
                if (zipFile.exists()) {
                    zipFile.delete();
                    logger.error(zipFilePath + "目录下存在名字为:" + fileName + ".zip" + "打包文件.已将其删除");
                }
                fos = new FileOutputStream(zipFile);
                zos = new ZipOutputStream(new BufferedOutputStream(fos));
                addZip(sourceFilePath, sourceFile, fis, bis, zos);
                flag = true;
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                //关闭流
                try {
                    if (null != zos) zos.close();
                    if (null != fos) fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
        return flag;
    }

    private static void addZip(String sourceFilePath, File filePath, FileInputStream fis, BufferedInputStream bis, ZipOutputStream zos) throws IOException {
        if (filePath.isDirectory()) {
            File[] sourceFiles = filePath.listFiles();
            if (null == sourceFiles || sourceFiles.length < 1) {
                logger.error("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");
            } else {
                for (File file : sourceFiles) {
                    addZip(sourceFilePath, file, fis, bis, zos);
                }
            }
        } else {
            //读取待压缩的文件并写进压缩包里
            fis = new FileInputStream(filePath);
            ZipEntry zipEntry = new ZipEntry(filePath.getAbsolutePath().substring(sourceFilePath.length()));
            zos.putNextEntry(zipEntry);
            bis = new BufferedInputStream(fis, 1024 * 10);
            byte[] bufs = new byte[1024 * 10];
            int read = 0;
            while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                zos.write(bufs, 0, read);
            }
            fis.close();
        }
    }

}
