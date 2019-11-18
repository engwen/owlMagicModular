package com.owl.util;

import java.io.*;

/**
 * 數組以及object互轉工具
 * object对象必须继承Serializable
 * @author engwen
 * email xiachanzou@outlook.com
 * 2017/4/14.
 */
public abstract class ObjectAndByteArrayUtil {
    /**
     * 将Object对象转为byte数组
     * @param obj obj
     * @return byte[]
     */
    public static byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        if (obj instanceof Serializable) {
            ByteArrayOutputStream byteIn = new ByteArrayOutputStream();
            try {
                ObjectOutputStream objectOut = new ObjectOutputStream(byteIn);
                objectOut.writeObject(obj);
                objectOut.flush();
                bytes = byteIn.toByteArray();
                objectOut.close();
                byteIn.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("please implements Serializable");
        }
        return bytes;
    }

    /**
     * 将byte数组转成Object对象
     * @param bytes byte[]
     * @return Object
     */
    public static Object toObject(byte[] bytes) {
        Object object = null;
        ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream objIn = new ObjectInputStream(byteIn);
            object = objIn.readObject();
            objIn.close();
            byteIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
