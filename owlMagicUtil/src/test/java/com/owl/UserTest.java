package com.owl;

import com.owl.pattern.observer.OwlObserved;
import com.owl.util.ObjectUtil;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.Map;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/10/30.
 */
public class UserTest extends OwlObserved {
    private String name;

    public void mackname(Map bb) {
        System.out.println(name);
        System.out.println(ObjectUtil.toJSON(bb));
        System.out.println("-----------");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Test
    public void tests() throws Exception{
        ScriptEngineManager scriptEngine = new ScriptEngineManager();
        ScriptEngine groovy = scriptEngine.getEngineByName("groovy");
        groovy.eval("def a=1, b=3;def c= a+b ;println(c)");

    }
}
