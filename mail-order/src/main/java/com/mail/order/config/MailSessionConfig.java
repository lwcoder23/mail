package com.mail.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration
public class MailSessionConfig {

    @Bean
    public CookieSerializer cookieSerializer() {

        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();

        // 放大作用域---放大到父域（认证页面回调是到 auth2.weiMail.com,
        // 得到 token 后跳转到 weiMail.com，此时存储 token的 jsessionID 是保存在子域 auth2.weiMail.com 中的）
        // 想要在 weiMail.com 父域 中获取 session 中的 token信息，
        // 就要将在子域中存储 cookie 的 jsessionID 拿到，所以要将 cookie作用域放大到父域
        cookieSerializer.setDomainName("weiMail.com");
        cookieSerializer.setCookieName("MAILSESSION");

        return cookieSerializer;
    }

    /**
     *  将 session 的默认序列化器换成 json格式的
     * @return
     */
    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

}
