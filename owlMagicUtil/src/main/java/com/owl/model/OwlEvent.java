package com.owl.model;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/11/26.
 */
public abstract class OwlEvent extends ModelPrototype {
    private String eventName;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
