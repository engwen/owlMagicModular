package com.owl.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/05/07.
 */
@Component
public class OwlCommentInit implements InitializingBean {
    private final Logger logger = LoggerFactory.getLogger(OwlCommentInit.class);
    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("owlMagicComment`s AOP init success");
    }
}
