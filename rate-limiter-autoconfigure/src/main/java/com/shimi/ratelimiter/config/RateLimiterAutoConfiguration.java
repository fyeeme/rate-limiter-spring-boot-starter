package com.shimi.ratelimiter.config;

import com.shimi.ratelimiter.handler.RateLimiterInterceptor;
import com.shimi.ratelimiter.service.RateLimiterService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@ConditionalOnClass(RateLimiterProperties.class)
@ConditionalOnProperty(value = "spring.rate-limiter.enable", havingValue = "true")
@EnableConfigurationProperties(RateLimiterProperties.class)
public class RateLimiterAutoConfiguration {

    @Bean
    public RateLimiterProperties properties() {
        return new RateLimiterProperties();
    }

    @Bean
    @ConditionalOnMissingBean
    public RateLimiterService rateLimiterService() {
        return new RateLimiterService(properties());
    }

    @Bean
    @ConditionalOnMissingBean
    public RateLimiterInterceptor rateLimiterInterceptor() {
        return new RateLimiterInterceptor(rateLimiterService(), properties());
    }

    @Bean
    @ConditionalOnWebApplication
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(rateLimiterInterceptor());
            }
        };
    }

}
