package com.owl.pattern.observer;


import com.owl.factory.OwlThreadPool;
import com.owl.model.OwlEvent;
import com.owl.pattern.function.base.OwlListenCodeBase;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 观察者抽象方法
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/4/26.
 */
public abstract class OwlObserverAB {
    /**
     * 保證綫程安全
     */
    private static Map<String, Set<OwlObserved>> mapList = new ConcurrentHashMap<>();

    /**
     * 添加对象监听
     * @param owlEvent   事件
     * @param model      对象
     * @param listenCode 将要执行的代码
     */
    static void addEventListen(OwlEvent owlEvent, OwlObserved model, OwlListenCodeBase listenCode) {
        //添加事件处理方法记录
        model.getConsumerMap().put(owlEvent.getEventName(), listenCode);
        //監聽對象注冊
        mapList.putIfAbsent(owlEvent.getEventName(), new HashSet<>());
        mapList.get(owlEvent.getEventName()).add(model);
    }

    /**
     * 移除指定对象的指定監聽事件
     * @param owlEvent 事件类型
     * @param model    对象
     */
    static void removeEventListen(OwlEvent owlEvent, OwlObserved model) {
        removeEventList(owlEvent, obj -> model == obj);
    }

    /**
     * 移除指定类的指定監聽事件
     * @param owlEvent   事件类型
     * @param classModel 类
     */
    public static void removeEventListen(OwlEvent owlEvent, Class classModel) {
        removeEventList(owlEvent, obj -> classModel.equals(obj.getClass()));
    }

    private static void removeEventList(OwlEvent owlEvent, Predicate<OwlObserved> predicate) {
        if (!mapList.containsKey(owlEvent.getEventName())) {
            return;
        }
        mapList.get(owlEvent.getEventName()).parallelStream().filter(predicate).collect(Collectors.toList()).forEach(it -> {
            it.removeListenByEvent(owlEvent);
            mapList.get(owlEvent.getEventName()).remove(it);
        });
    }

    /**
     * 移除指定的監聽
     * @param owlEvent 事件类型
     */
    public static void removeEventListen(OwlEvent owlEvent) {
        if (mapList.containsKey(owlEvent.getEventName())) {
            //移除相關類中的監聽數據
            mapList.get(owlEvent.getEventName()).forEach(it -> it.removeListenByEvent(owlEvent));
            //移除本類中的監聽數據
            mapList.remove(owlEvent.getEventName());
        }
    }

    /**
     * 移除全部監聽
     */
    public static void removeAllEventListen() {
        //清除繼承類中的監聽數據
        mapList.values().forEach((Set<OwlObserved> it) -> it.forEach(OwlObserved::removeAllListen));
        //移除本類中的監聽數據
        mapList = null;
        mapList = new ConcurrentHashMap<>();
    }

    /**
     * 移除指定对象的監聽
     * @param models 对象集合
     */
    public static void removeAllEventListenByObserved(OwlObserved... models) {
        List<OwlObserved> modelList = Arrays.asList(models);
        mapList.keySet().forEach(key ->
                mapList.get(key).stream().filter(modelList::contains).collect(Collectors.toList()).forEach(owlObserved ->
                        mapList.get(key).remove(owlObserved)
                )
        );
        modelList.forEach(OwlObserved::removeAllListen);
    }

    /**
     * 抛出事件
     * @param owlEvent 事件
     */
    public static void dispatchEvent(OwlEvent owlEvent, Object... params) {
        dispatchEvent(owlEvent, obj -> true, params);
    }

    /**
     * /**
     * 抛出事件
     * @param owlEvent   事件
     * @param classModel 类
     */
    public static void dispatchEvent(OwlEvent owlEvent, Class classModel, Object... params) {
        dispatchEvent(owlEvent, obj -> obj.getClass().equals(classModel), params);
    }

    private static void dispatchEvent(OwlEvent owlEvent, Predicate<OwlObserved> predicate, Object... params) {
        if (null == mapList.get(owlEvent.getEventName())) {
            return;
        }
        Set<OwlObserved> observedSet = new HashSet<>(mapList.get(owlEvent.getEventName()));
        observedSet.stream().filter(predicate).collect(Collectors.toList()).forEach(it ->
//                            it.startDoing(owlObserverEvent, params)
                        OwlThreadPool.getThreadPool().execute(() -> it.startDoing(owlEvent, params))
        );

    }
}
