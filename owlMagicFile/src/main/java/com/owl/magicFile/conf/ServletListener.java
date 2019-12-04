package com.owl.magicFile.conf;

import com.owl.util.LogPrintUtil;
import com.owl.util.PropertiesUtil;
import org.springframework.beans.factory.InitializingBean;

import java.io.File;
import java.nio.ByteBuffer;


/**
 * 項目初始化
 * author engwen
 * email xiachanzou@outlook.com
 * time 2017/12/04.
 */
public class ServletListener implements InitializingBean {//implements InitializingBean

    public void afterPropertiesSet() throws Exception {
        LogPrintUtil.info("owlMagicFile init now");
        File fileDir = new File(PropertiesUtil.readProperties("file_setting","upload.path.dir"));
        if (!fileDir.exists()) {
            LogPrintUtil.info("create dir for upload files");
            LogPrintUtil.info("the dir path is " + fileDir.getAbsolutePath());
            fileDir.mkdir();
        }
    }
}
