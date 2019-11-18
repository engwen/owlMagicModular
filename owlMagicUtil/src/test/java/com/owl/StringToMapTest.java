package com.owl;

import com.owl.util.LogPrintUtil;
import com.owl.util.ObjectUtil;
import org.junit.Test;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/11/18.
 */
public class StringToMapTest {

    @Test
    public void start() {
        LogPrintUtil.info(ObjectUtil.StringToMap("{\"name\":\"zhangsan\",\"age\":\"12\",\"user\":[{\"name\":\"zhangsan\",\"age\":\"12\",\"pageList\":[\"1\",\"2\"]},{\"name\":\"zhangsan\",\"age\":\"12\"}],\"pageList\":[\"1\",\"2\"]}}"));
        LogPrintUtil.info(ObjectUtil.StringToList("[{\"name\":\"zhangsan\",\"age\":\"12\",\"pageList\":[\"1\",\"2\"]},{\"name\":\"zhangsan\",\"age\":\"12\"}]"));
    }
}
