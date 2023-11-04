package com.mail.order.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class MailFeignConfig {

    /**
     *  解决远程调用时丢失请求头的问题
     * @return
     */
    @Bean("requestInterceptor")
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                // RequestContextHolder spring提供的拿到当前线程的上下文信息
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

                // 同步所有原请求头信息
                HttpServletRequest request = attributes.getRequest(); // 拿到老的请求
                // 添加原请求头中的 Cookie信息
                requestTemplate.header("Cookie", request.getHeader("Cookie"));
            }
        };
    }

}
