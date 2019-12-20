package com.owl.io.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 文件处理
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/12/11.
 */
public class FileIO {


    /**
     * 将字符串写入指定文件
     * @param dir
     * @param fileName
     * @param content
     * @throws IOException
     */
    public static void writeFile(String dir, String fileName, String content) {
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        String filePath = dir + File.separator + fileName;
        File file = new File(filePath);
        ByteBuffer buffer = ByteBuffer.wrap(content.getBytes());
        FileChannel outChannel = null;
        FileOutputStream fo = null;
        try {
            if (file.createNewFile()) {
                fo = new FileOutputStream(file);
                outChannel = fo.getChannel();
                outChannel.write(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != outChannel) {
                    outChannel.close();
                }
                if (null != fo) {
                    fo.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取指定文件的内容并返回
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String readFile(String filePath) {
        StringBuilder result = new StringBuilder("");
        File dirFile = new File(filePath);
        if (!dirFile.exists()) {
            return result.toString();
        }
        File file = new File(filePath);
        FileInputStream fi = null;
        FileChannel inChannel = null;

        try {
            fi = new FileInputStream(file);
            inChannel = fi.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (true) {
                int read = inChannel.read(buffer);
                if (read == -1) {
                    break;
                }
                result.append(new String(buffer.array()));
                buffer.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != inChannel) {
                    inChannel.close();
                }
                if (null != fi) {
                    fi.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }

    public static void copyFileUseNIO(String sourcePath, String destinationPath) {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            fi = new FileInputStream(new File(sourcePath));
            fo = new FileOutputStream(new File(destinationPath));
            inChannel = fi.getChannel();
            outChannel = fo.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (true) {
                int eof = inChannel.read(buffer);
                if (eof == -1) {
                    break;
                }
                buffer.flip();
                outChannel.write(buffer);
                buffer.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != inChannel) {
                    inChannel.close();
                }
                if (null != outChannel) {
                    outChannel.close();
                }
                if (null != fi) {
                    fi.close();
                }
                if (null != fo) {
                    fo.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
