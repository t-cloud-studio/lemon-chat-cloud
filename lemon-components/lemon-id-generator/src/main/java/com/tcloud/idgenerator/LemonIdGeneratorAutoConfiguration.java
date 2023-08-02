package com.tcloud.idgenerator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LemonIdGeneratorAutoConfiguration implements InitializingBean {


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("--- distributed id generator is started ---");
    }
}
