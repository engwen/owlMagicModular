package com.owl;

import com.owl.pattern.observer.OwlObserved;
import com.owl.pattern.observer.OwlObserverAB;
import com.owl.pattern.observer.OwlObserverEvent;
import com.owl.util.ObjectUtil;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/10/28.
 */
public class UserTe {

    @Test
    public void taa() throws Exception {
        UserTest mimi = new UserTest();
        mimi.setName("mimi");
        OwlObserverEvent outName = new OwlObserverEvent("getName");
//        mimi.addEventListen(outName,()-> System.out.println("buyaolian"));

//        mimi.addEventListen(outName,(it)-> {
//            try {
//                Method mackname = mimi.getClass().getMethod("mackname", Object.class);
//                mackname.invoke(mimi,"ddd");
//            }catch (Exception e){
//
//            }
//
//        });
        mimi.addEventListen(outName, "mackname");
//        mimi.addEventListen(outName,(it)-> {
//            System.out.println(it.length);
//            System.out.println( ((Map)it[0]).get("name"));
//        });
        Map map = new HashMap();
        map.put("name", "test");
        map.put("value", "321");
        mimi.dispatchEvent(outName, map);

//        Class<?> aClass = Class.forName(mimi.getClass().getName());

    }

}
