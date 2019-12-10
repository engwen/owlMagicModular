package com.owl;

import com.owl.util.LogPrintUtil;
import org.junit.jupiter.api.Test;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/12/10.
 */
public class DataCountTest {

    @Test
    public void start()  {
        String crontab = "0 * * * *";
        String[] s = crontab.split(" ");
        LogPrintUtil.info(s);
    }
}
