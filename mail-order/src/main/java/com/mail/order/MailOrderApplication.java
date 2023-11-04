package com.mail.order;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRabbit
@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
@EnableRedisHttpSession
@EnableAspectJAutoProxy(exposeProxy = true)     //开启了aspect动态代理模式,对外暴露代理对象, 解决调用本类对象事务失效的问题
public class MailOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailOrderApplication.class, args);
	}

}

