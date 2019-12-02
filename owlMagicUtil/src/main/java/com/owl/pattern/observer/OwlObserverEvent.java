package com.owl.pattern.observer;

import com.owl.model.OwlEvent;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/4/26.
 */
public class OwlObserverEvent extends OwlEvent {
    public OwlObserverEvent(String eventName) {
        super.setEventName(eventName);
    }
}
