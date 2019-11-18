package com.owl.pattern.observer;


import com.owl.pattern.function.ListenCodeMethod;
import com.owl.pattern.function.ListenCodeNoParams;
import com.owl.pattern.function.ListenCodeParams;
import com.owl.pattern.function.base.OwlListenCodeBase;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 被观察者抽象
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/4/26.
 */
public abstract class OwlObserved {
    /**
     * 保證綫程安全
     */
    private Map<String, OwlListenCodeBase> consumerMap = new ConcurrentHashMap<>();

    Map<String, OwlListenCodeBase> getConsumerMap() {
        return consumerMap;
    }

    void setConsumerMap(Map<String, OwlListenCodeBase> consumerMap) {
        this.consumerMap = consumerMap;
    }

    /**
     * 被觀察者監聽事件
     * @param event      事件
     * @param listenCode 待执行代码
     */
    public void addEventListen(OwlObserverEvent event, ListenCodeNoParams listenCode) {
        //注冊驅動
        OwlObserverAB.addEventListen(event, this, listenCode);
    }

    /**
     * 被觀察者監聽事件
     * @param event      事件
     * @param listenCode 待执行代码
     */
    public void addEventListen(OwlObserverEvent event, ListenCodeParams listenCode) {
        //注冊驅動
        OwlObserverAB.addEventListen(event, this, listenCode);
    }

    /**
     * 被觀察者監聽事件
     * @param event      事件
     * @param methodName 待执行代码
     */
    public void addEventListen(OwlObserverEvent event, String methodName) {
        //注冊驅動
        ListenCodeMethod listenCodeMethod = new ListenCodeMethod();
        listenCodeMethod.setOwlObserved(this);
        listenCodeMethod.setMethodName(methodName);
        OwlObserverAB.addEventListen(event, this, listenCodeMethod);
    }

    public void removeEventListen(OwlObserverEvent event) {
        OwlObserverAB.removeEventListen(event, this);
    }

    public void removeAllEventListen() {
        OwlObserverAB.removeAllEventListenByObserved(this);
    }

    /**
     * 抛出
     * @param owlObserverEvent 将要抛出的事件
     * @param params           参数
     */
    public void dispatchEvent(OwlObserverEvent owlObserverEvent, Object... params) {
        OwlObserverAB.dispatchEvent(owlObserverEvent, params);
    }

    /**
     * 抛出
     * @param owlObserverEvent 将要抛出的事件
     */
    public void dispatchEvent(OwlObserverEvent owlObserverEvent) {
        OwlObserverAB.dispatchEvent(owlObserverEvent);
    }

    /**
     * 抛出
     * @param owlObserverEvent 将要抛出的事件
     * @param classModel       响应的类
     */
    public void dispatchEvent(OwlObserverEvent owlObserverEvent, Class classModel) {
        OwlObserverAB.dispatchEvent(owlObserverEvent, classModel);
    }

    /*------------------           以下方法提供給 OwlObserverAB 用於數據同步/脚本執行           ---------------------*/

    /**
     * 移除指定事件
     * @param event 事件类型
     */
    void removeListenByEvent(OwlObserverEvent event) {
        if (null != consumerMap.get(event.getEventName())) {
            consumerMap.remove(event.getEventName());
        }
    }

    /**
     * 移除所有事件
     */
    void removeAllListen() {
        consumerMap = null;
        consumerMap = new ConcurrentHashMap<>();
    }

    /**
     * 被觀察者需要执行的代碼
     * @param event 事件
     */
    void startDoing(OwlObserverEvent event, Object... params) {
        OwlListenCodeBase listenCode = consumerMap.get(event.getEventName());
        if (null != listenCode) {
            //執行
            if (listenCode instanceof ListenCodeNoParams) {
                ((ListenCodeNoParams) listenCode).startDoing();
            } else if (listenCode instanceof ListenCodeParams) {
                ((ListenCodeParams) listenCode).startDoing(params);
            } else if (listenCode instanceof ListenCodeMethod) {
                ((ListenCodeMethod) listenCode).startDoing(params);
            }
        }
    }
}
