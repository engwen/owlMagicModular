package com.owl.util;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 将文件夹下面的文件
 * 打包成zip压缩文件
 * 这个方法里面的文件夹不能为空
 * @author admin
 */
public abstract class FileToZip {
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
            LogPrintUtil.error("待压缩的文件目录：" + sourceFilePath + "不存在.");
        } else {
            try {
                File zipFile = new File(zipFilePath + File.separator + fileName + ".zip");
                if (zipFile.exists()) {
                    zipFile.delete();
                    LogPrintUtil.error(zipFilePath + "目录下存在名字为:" + fileName + ".zip" + "打包文件.已将其删除");
                }
                File[] sourceFiles = sourceFile.listFiles();
                if (null == sourceFiles || sourceFiles.length < 1) {
                    LogPrintUtil.error("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");
                } else {
                    fos = new FileOutputStream(zipFile);
                    zos = new ZipOutputStream(new BufferedOutputStream(fos));
                    byte[] bufs = new byte[1024 * 10];
                    for (File file : sourceFiles) {
                        //创建ZIP实体，并添加进压缩包
                        ZipEntry zipEntry = new ZipEntry(file.getName());
                        zos.putNextEntry(zipEntry);
                        //读取待压缩的文件并写进压缩包里
                        fis = new FileInputStream(file);
                        bis = new BufferedInputStream(fis, 1024 * 10);
                        int read = 0;
                        while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                            zos.write(bufs, 0, read);
                        }
                    }
                    flag = true;
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                //关闭流  
                try {
                    if (null != bis) bis.close();
                    if (null != zos) zos.close();
                    if (null != fis) fis.close();
                    if (null != fos) fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
        return flag;
    }

    /**
     * 传入list的文件路径，打包到指定的新生成ZIP文件中，如果已存在的Zip，将会被覆盖
     * <p>
     * 特注：zos一定放在循环外结束，不然我打不死你
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
        for (int i = 0; i < path.size(); i++) {
            if (null != path.get(i)) {
                continue;
            }
            File sourceFile = new File(path.get(i));
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

}  