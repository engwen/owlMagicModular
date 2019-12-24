package com.owl.shiro.vo;

import com.owl.pattern.observer.OwlObserved;
import com.owl.pattern.observer.OwlObserverAB;
import com.owl.pattern.observer.OwlObserverEvent;

/**
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/8/26.
 */
public class TestVO {
    private String msg;
    private String code;
    private Object data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
    //build class
    class UserTest extends OwlObserved {

    }

    class test{
        // build event
        OwlObserverEvent HH= new OwlObserverEvent("HH");
        //add event listen

        public UserTest test() {
            UserTest lili = new UserTest();
            lili.addEventListen(HH,()->{System.out.println("33333");});
            lili.dispatchEvent(HH);
            lili.removeEventListen(HH);
            OwlObserverAB.dispatchEvent(HH);
            return null;
        }
    }
