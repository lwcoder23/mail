package com.mail.cart.config;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

@Configuration
public class MyThreadConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor(ThreadPoolConfigProperties pool) {
        return new ThreadPoolExecutor(pool.getCoreSize(),
                                      pool.getMaxSize(),
                                      pool.getKeepAliveTime(),
                                      TimeUnit.SECONDS,
                                      new LinkedBlockingDeque<>(),
                                      Executors.defaultThreadFactory(),
                                      new ThreadPoolExecutor.AbortPolicy());
    }

}
