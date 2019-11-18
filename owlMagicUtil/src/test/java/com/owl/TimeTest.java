package com.owl;

import com.owl.pattern.function.ListenCodeNoParams;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/9/27.
 */
public class TimeTest {

    @Test
    public void build() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println("你".getBytes());
//        ff(this,"toString");
        String b = "ccc";
        ListenCodeNoParams listenCode = () -> {
            System.out.println("aaaa");
        };

//        Method method = this.getClass().getMethod("bbbb");
//        Object[] a = {"a","b"};
//        method.invoke(this);
//        System.out.println(DateCountUtil.getDateFormSdf(new Date(1570861438000L + 8 * 60 *60 *1000),DateCountUtil.YMDHMS4BAR));
    }


    public static void ff(Object obj, String method) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method1 = obj.getClass().getMethod(method);
        Object invoke = method1.invoke(obj);
    }

    public String bbbb(Object[] a) {
        System.out.println("hhh");
        return null;
    }
}
