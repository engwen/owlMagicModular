package com.owl.core;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/12/22.
 */
public class Generator {
    public void generator() throws Exception{
        List<String> warnings = new ArrayList<>();
        File configFile = new File("src/main/resources/generatorConfig.xml");
        //替换ConfigurationParser
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }
}
