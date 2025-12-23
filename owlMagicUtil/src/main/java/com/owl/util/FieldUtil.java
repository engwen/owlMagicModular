package com.owl.util;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Function;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2023/6/8.
 */
public abstract class FieldUtil {
    /*
     * 獲取一個對象的全部屬性
     * @param obj
     * @param fields
     * @return
     */
    public static Field[] getSupperClassProperties(Object obj) {
        return FieldUtil.getSupperClassProperties(obj, new Field[0]);
    }

    /*
     * 獲取一個對象的全部屬性
     * @param obj
     * @param fields
     * @return
     */
    private static Field[] getSupperClassProperties(Object obj, Field[] fields) {
        for (Class<?> classTemp = obj.getClass(); classTemp != Object.class; classTemp = classTemp.getSuperclass()) {
            try {
                Field[] fieldsTemp = classTemp.getDeclaredFields();
                fields = Arrays.copyOf(fields, fields.length + fieldsTemp.length);
                System.arraycopy(fieldsTemp, 0, fields, fields.length - fieldsTemp.length, fieldsTemp.length);
            } catch (Exception e) {
                //我們只是這樣寫而已
            }
        }
        return fields;
    }


    /**
     * 对外提供获取类的指定名称的field
     * @param clazz     class类对象
     * @param fieldName field名称
     * @return field对象
     * @throws NoSuchFieldException 无改field抛出异常
     */
    public static Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        Field field = null;
        for (Class<?> classTemp = clazz; classTemp != Object.class; classTemp = classTemp.getSuperclass()) {
            try {
                field = classTemp.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException noSuchFieldException) {
                //只是這樣寫而已
            }
        }
        if (null == field) {
            throw new NoSuchFieldException();
        }
        return field;
    }

    /**
     * 将bean的属性的get方法，作为lambda表达式传入时，获取get方法对应的属性Field
     * @param fn  lambda表达式，bean的属性的get方法
     * @param <T> 泛型
     * @return 属性对象
     */
    public static <T> String getFieldName(Function<T, ?> fn) {
        // 从function取出序列化方法
        Method writeReplaceMethod;
        try {
            writeReplaceMethod = fn.getClass().getDeclaredMethod("writeReplace");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        // 从序列化方法取出序列化的lambda信息
        boolean isAccessible = writeReplaceMethod.isAccessible();
        writeReplaceMethod.setAccessible(true);
        SerializedLambda serializedLambda;
        try {
            serializedLambda = (SerializedLambda) writeReplaceMethod.invoke(fn);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        writeReplaceMethod.setAccessible(isAccessible);
        // 从lambda信息取出method、field、class等
        String implMethodName = serializedLambda.getImplMethodName();
        // 确保方法是符合规范的get方法，boolean类型是is开头
        if (!implMethodName.startsWith("is") && !implMethodName.startsWith("get") && !implMethodName.startsWith("set")) {
            throw new RuntimeException("get 或 set 方法名称: " + implMethodName + ", 不符合java bean规范");
        }
        // get方法开头为 is 或者 get，将方法名 去除is或者get，然后首字母小写，就是属性名
        int prefixLen = implMethodName.startsWith("is") ? 2 : 3;
        String fieldName = implMethodName.substring(prefixLen);
        String firstChar = fieldName.substring(0, 1);
        fieldName = fieldName.replaceFirst(firstChar, firstChar.toLowerCase());
        return fieldName;
    }

    /**
     * 将bean的属性的get方法，作为lambda表达式传入时，获取get方法对应的属性Field
     * @param fn  lambda表达式，bean的属性的get方法
     * @param <T> 泛型
     * @return 属性对象
     */
    public static <T> Field getField(Function<T, ?> fn) {
        // 从function取出序列化方法
        Method writeReplaceMethod;
        try {
            writeReplaceMethod = fn.getClass().getDeclaredMethod("writeReplace");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        // 从序列化方法取出序列化的lambda信息
        boolean isAccessible = writeReplaceMethod.isAccessible();
        writeReplaceMethod.setAccessible(true);
        SerializedLambda serializedLambda;
        try {
            serializedLambda = (SerializedLambda) writeReplaceMethod.invoke(fn);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        writeReplaceMethod.setAccessible(isAccessible);
        // 从lambda信息取出method、field、class等
        String implMethodName = serializedLambda.getImplMethodName();
        // 确保方法是符合规范的get方法，boolean类型是is开头
        if (!implMethodName.startsWith("is") && !implMethodName.startsWith("get") && !implMethodName.startsWith("set")) {
            throw new RuntimeException("get 或 set 方法名称: " + implMethodName + ", 不符合java bean规范");
        }
        // get方法开头为 is 或者 get，将方法名 去除is或者get，然后首字母小写，就是属性名
        int prefixLen = implMethodName.startsWith("is") ? 2 : 3;
        String fieldName = implMethodName.substring(prefixLen);
        String firstChar = fieldName.substring(0, 1);
        fieldName = fieldName.replaceFirst(firstChar, firstChar.toLowerCase());
        Field field;
        try {
            field = Class.forName(serializedLambda.getImplClass().replace("/", ".")).getDeclaredField(fieldName);
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        return field;
    }


    public static void setFieldValue(Object object, String fieldName, Object value) {
        // 获取对象的Class对象
        Class<?> clazz = object.getClass();
        // 获取字段对象
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
            // 确保字段是可访问的
            field.setAccessible(true);
            // 为字段赋值
            field.set(object, value);
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
//            throw new RuntimeException(e);
        }
    }

    public static Object getFieldValue(Object object, String fieldName) {
        // 获取对象的Class对象
        Class<?> clazz = object.getClass();
        // 获取字段对象
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
            // 确保字段是可访问的
            field.setAccessible(true);
            // 为字段赋值
            return field.get(object);
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
//            throw new RuntimeException(e);
        }
        return null;
    }

}
