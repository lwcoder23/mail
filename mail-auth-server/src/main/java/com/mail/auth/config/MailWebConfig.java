package com.mail.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MailWebConfig implements WebMvcConfigurer {

    /**·
     * SpringMVC - WebMvcConfigurer - ViewController(addViewControllers)
     * 视图映射: 发送一个请求，直接映射到一个页面
     * 默认是 get 的请求方式
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // registry.addViewController("/login.html").setViewName("login");
        registry.addViewController("/reg.html").setViewName("reg");
    }
}
