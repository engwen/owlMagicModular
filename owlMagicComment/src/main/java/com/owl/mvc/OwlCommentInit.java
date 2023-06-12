package com.owl.mvc;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/05/07.
 */
@Component
public class OwlCommentInit implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("\n /~~\\       ||\\  /|        '   \n" +
                "|    |\\    /|| \\/ |/~~|/~~||/~~\n" +
                " \\__/  \\/\\/ ||    |\\__|\\__||\\__\n" +
                "                       \\__|    " +
                "");
    }
}
