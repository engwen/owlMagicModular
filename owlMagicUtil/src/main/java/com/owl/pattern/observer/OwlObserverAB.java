package com.owl.pattern.observer;


import com.owl.factory.OwlThreadPool;
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
     * @param owlObserverEvent 事件
     * @param model            对象
     * @param listenCode       将要执行的代码
     */
    static void addEventListen(OwlObserverEvent owlObserverEvent, OwlObserved model, OwlListenCodeBase listenCode) {
        //添加事件处理方法记录
        model.getConsumerMap().put(owlObserverEvent.getEventName(), listenCode);
        //監聽對象注冊
        mapList.putIfAbsent(owlObserverEvent.getEventName(), new HashSet<>());
        mapList.get(owlObserverEvent.getEventName()).add(model);
    }

    /**
     * 移除指定对象的指定監聽事件
     * @param owlObserverEvent 事件类型
     * @param model            对象
     */
    static void removeEventListen(OwlObserverEvent owlObserverEvent, OwlObserved model) {
        removeEventList(owlObserverEvent, obj -> model == obj);
    }

    /**
     * 移除指定类的指定監聽事件
     * @param owlObserverEvent 事件类型
     * @param classModel       类
     */
    public static void removeEventListen(OwlObserverEvent owlObserverEvent, Class classModel) {
        removeEventList(owlObserverEvent, obj -> classModel.equals(obj.getClass()));
    }

    private static void removeEventList(OwlObserverEvent owlObserverEvent, Predicate<OwlObserved> predicate) {
        if (!mapList.containsKey(owlObserverEvent.getEventName())) {
            return;
        }
        mapList.get(owlObserverEvent.getEventName()).parallelStream().filter(predicate).collect(Collectors.toList()).forEach(it -> {
            it.removeListenByEvent(owlObserverEvent);
            mapList.get(owlObserverEvent.getEventName()).remove(it);
        });
    }

    /**
     * 移除指定的監聽
     * @param owlObserverEvent 事件类型
     */
    public static void removeEventListen(OwlObserverEvent owlObserverEvent) {
        if (mapList.containsKey(owlObserverEvent.getEventName())) {
            //移除相關類中的監聽數據
            mapList.get(owlObserverEvent.getEventName()).forEach(it -> it.removeListenByEvent(owlObserverEvent));
            //移除本類中的監聽數據
            mapList.remove(owlObserverEvent.getEventName());
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
     * @param owlObserverEvent 事件
     */
    public static void dispatchEvent(OwlObserverEvent owlObserverEvent, Object... params) {
        dispatchEvent(owlObserverEvent, obj -> true, params);
    }

    /**
     * /**
     * 抛出事件
     * @param owlObserverEvent 事件
     * @param classModel       类
     */
    public static void dispatchEvent(OwlObserverEvent owlObserverEvent, Class classModel, Object... params) {
        dispatchEvent(owlObserverEvent, obj -> obj.getClass().equals(classModel), params);
    }

    private static void dispatchEvent(OwlObserverEvent owlObserverEvent, Predicate<OwlObserved> predicate, Object... params) {
        if (null == mapList.get(owlObserverEvent.getEventName())) {
            return;
        }
        Set<OwlObserved> observedSet = new HashSet<>(mapList.get(owlObserverEvent.getEventName()));
        observedSet.stream().filter(predicate).collect(Collectors.toList()).forEach(it ->
//                            it.startDoing(owlObserverEvent, params)
                        OwlThreadPool.getThreadPool().execute(() -> it.startDoing(owlObserverEvent, params))
        );

    }
}
