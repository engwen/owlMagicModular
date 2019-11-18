package com.owl.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 对象工具类
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/2/28.
 */
public class ObjectUtil {
    private static Logger logger = Logger.getLogger(ObjectUtil.class.getName());

    /**
     * 將一个对象的所有属性赋值给另一个对象的对应属性，以旧的属性为主体
     * @param oldObj 主要對象
     * @param newObj 將要塞入的對象
     * @param <T>    类型
     * @param <K>    类型
     * @return 返回原對象
     */
    public static <T, K> K setThisObjToAnotherObj(T oldObj, K newObj) {
        Field[] oldFields = getSupperClassProperties(oldObj, new Field[0]);
        Field[] newFields = getSupperClassProperties(newObj, new Field[0]);
        for (Field oldField : oldFields) {
            for (Field newField : newFields) {
                if (oldField.getName().equals(newField.getName())) {
                    try {
                        String methodStr = oldField.getName().substring(0, 1).toUpperCase() + oldField.getName().substring(1);
                        String setMethodStr = "set" + methodStr;
                        String getMethodStr = "get" + methodStr;
                        Method getMethod = oldObj.getClass().getMethod(getMethodStr);
                        Object value = getMethod.invoke(oldObj);
                        Method setMethod = newObj.getClass().getMethod(setMethodStr, value.getClass());
                        setMethod.invoke(newObj, value);
                    } catch (Exception e) {
                        System.out.println("This property does not exist in the object.skip name:" + oldField.getName());
                    }
                }
            }
        }
        return newObj;
    }


    /*
     * 獲取一個對象的全部屬性
     * @param obj
     * @param fields
     * @return
     */
    public static Field[] getSupperClassProperties(Object obj, Field[] fields) {
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

    /*
     * 獲取一個對象的指定的屬性
     * @param proName
     * @param obj
     * @return
     * @throws Exception
     */
    public static Object getProValue(String proName, Object obj) throws Exception {
        String getMethodStr = "get" + proName.substring(0, 1).toUpperCase() + proName.substring(1);
        Field[] fields = getSupperClassProperties(obj, new Field[0]);
        for (Field field : fields) {
            if (proName.equals(field.getName())) {
                Method getMethod = obj.getClass().getMethod(getMethodStr);
                return getMethod.invoke(obj);
            }
        }
        return null;
    }

    /*
     * 設置一個對象的指定的屬性
     * @param proName
     * @param obj
     * @return
     * @throws Exception
     */
    public static boolean setProValue(String proName, Object proValue, Object obj) throws Exception {
        String setMethodStr = "set" + proName.substring(0, 1).toUpperCase() + proName.substring(1);
        Field[] fields = getSupperClassProperties(obj, new Field[0]);
        for (Field field : fields) {
            if (proName.equals(field.getName())) {
                try {
                    Method setMethod = obj.getClass().getMethod(setMethodStr, proValue.getClass());
                    setMethod.invoke(obj, proValue);
                    return true;
                } catch (Exception e) {
                    logger.warning("没有查询到对应属性方法,尝试进行Object对象插入。此方法同样适用适用泛型对象");
                    try {
                        Method setMethod = obj.getClass().getMethod(setMethodStr, Object.class);
                        setMethod.invoke(obj, proValue);
                        return true;
                    } catch (NoSuchMethodException ex) {
                        logger.warning(String.format("插入%s属性失败，请检查方法%s的返回类型，并确保类型一致", proName, setMethodStr));
                        throw ex;
                    }
                }
            }
        }
        return false;
    }


    /*
     * 将指定的一级对象转化为json字符串
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String toJSON(T obj) {
        StringBuilder stringBuilder = new StringBuilder();
        objectJSONValue(obj, stringBuilder);
        return stringBuilder.toString();
    }

    private static void getObjectProperties(Object obj, StringBuilder stringBuilder) {
        if (obj instanceof Map) {
            stringBuilder.append("{");
            ((Map) obj).keySet().forEach(key -> {
                stringBuilder.append("\"");
                stringBuilder.append(key);
                stringBuilder.append("\"");
                stringBuilder.append(":");
                Object temp = ((Map) obj).get(key);
                objectJSONValue(temp, stringBuilder);
                stringBuilder.append(",");
            });
            if (((Map) obj).keySet().size() > 0) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }
            stringBuilder.append("}");
        } else {
            Field[] fields = obj.getClass().getDeclaredFields();
            Field errorFiled = null;
            try {
                stringBuilder.append("{");
                for (Field field : fields) {
                    errorFiled = field;
                    String getMethodStr = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                    Method getMethod = obj.getClass().getMethod(getMethodStr);
                    Object value = getMethod.invoke(obj);
                    stringBuilder.append("\"");
                    stringBuilder.append(field.getName());
                    stringBuilder.append("\"");
                    stringBuilder.append(":");
                    objectJSONValue(value, stringBuilder);
                    stringBuilder.append(",");
                }
                if (fields.length > 0) {
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                }
                stringBuilder.append("}");
            } catch (Exception e) {
                System.out.println("转化出错。name:" + (errorFiled == null ? "" : errorFiled.getName()));
                e.printStackTrace();
            }
        }
    }

    private static void objectJSONValue(Object temp, StringBuilder stringBuilder) {
        if (temp instanceof Method) {
            return;
        }
        if (ClassTypeUtil.isArrayClass(temp)) {
            if (temp instanceof int[]) {
                stringBuilder.append(Arrays.toString((int[]) temp));
            } else if (temp instanceof String[]) {
                stringBuilder.append(Arrays.toString((String[]) temp));
            } else if (temp instanceof double[]) {
                stringBuilder.append(Arrays.toString((double[]) temp));
            } else if (temp instanceof float[]) {
                stringBuilder.append(Arrays.toString((float[]) temp));
            } else {
                stringBuilder.append(Arrays.toString((Object[]) temp));
            }
        } else if (temp instanceof Collection) {
            Object[] list = ((Collection) temp).toArray();
            stringBuilder.append("[");
            for (Object o : list) {
                objectJSONValue(o, stringBuilder);
                stringBuilder.append(",");
            }
            if (list.length > 0) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }
            stringBuilder.append("]");
        } else if (null == temp) {
            stringBuilder.append("null");
        } else if (ClassTypeUtil.isPackClass(temp) || ClassTypeUtil.isBaseClass(temp)) {
            stringBuilder.append("\"");
            stringBuilder.append(temp);
            stringBuilder.append("\"");
        } else {
            getObjectProperties(temp, stringBuilder);
        }
    }
}
