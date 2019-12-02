package com.owl;

import com.owl.mvc.vo.PageVO;
import com.owl.util.DateCountUtil;
import com.owl.util.LogPrintUtil;
import org.junit.Test;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/9/27.
 */
public class TimeTest {

    @Test
    public void build() {
        List<String> t = new ArrayList<>();
        t.add("ddd");
        PageVO<String> pageVO = new PageVO<>();
        pageVO.setObjectList(t);
        Integer a = null;
        Integer b = 1;
        LogPrintUtil.info(Integer.valueOf(1).equals( a));
        LogPrintUtil.info(Integer.valueOf(1)== a);
        System.out.println(DateCountUtil.getDateFormSdf(new Date(1569582113000L + 8 * 60 * 60 * 1000), DateCountUtil.YMDHMS4BAR));
    }

}
