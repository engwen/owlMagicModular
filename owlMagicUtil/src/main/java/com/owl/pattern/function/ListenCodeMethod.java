package com.owl.pattern.function;

import com.owl.pattern.function.base.OwlListenCodeBase;
import com.owl.pattern.observer.OwlObserved;
import com.owl.util.LogPrintUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 待执行代码对象
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/10/30.
 */
public class ListenCodeMethod implements OwlListenCodeBase {
    private OwlObserved owlObserved;
    private String methodName;

    public void startDoing(Object... params) {
        try {
            List<Method> methodList = Arrays.stream(owlObserved.getClass().getMethods()).filter(it -> it.getName().equals(methodName)).collect(Collectors.toList());
            if (methodList.size() > 0) {
                for (Method method : methodList) {
                    try {
                        method.invoke(owlObserved, params);
                        break;
                    } catch (IllegalAccessException e) {
                        LogPrintUtil.error("params is error. More...");
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        LogPrintUtil.error("listen method is error. More...");
                        e.printStackTrace();
                    }
                }
            } else {
                throw new NoSuchMethodException();
            }
        } catch (NoSuchMethodException e) {
            LogPrintUtil.error("not find method " + methodName + " in this class,please check it. More...");
            e.printStackTrace();
        }
    }

    public OwlObserved getOwlObserved() {
        return owlObserved;
    }

    public void setOwlObserved(OwlObserved owlObserved) {
        this.owlObserved = owlObserved;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
