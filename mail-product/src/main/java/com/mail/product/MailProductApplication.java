package com.mail.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@MapperScan("com.mail.product.dao")
@EnableDiscoveryClient
@SpringBootApplication
public class MailProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailProductApplication.class, args);
	}

}
