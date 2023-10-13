package com.mail.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableCaching
@SpringBootApplication
@EnableFeignClients(basePackages = "com.mail.product.feign")
public class MailProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailProductApplication.class, args);
	}

}
