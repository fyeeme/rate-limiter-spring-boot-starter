package com.young.ratelimiter.config;

import com.young.ratelimiter.handler.RateLimiterInterceptor;
import com.young.ratelimiter.service.RateLimiterService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Autoconfiguration class for rate limiter.
 * This configuration class is used to automatically configure rate limiter services and interceptors.
 */
@Configuration
//@ConditionalOnClass(RateLimiterProperties.class)
@ConditionalOnProperty(value = "spring.rate-limiter.enable", havingValue = "true")
@EnableConfigurationProperties(RateLimiterProperties.class)
public class RateLimiterAutoConfiguration {

    /**
     * Configures the rate limiter properties.
     *
     * @return An instance of RateLimiterProperties.
     */
    @Bean
    public RateLimiterProperties properties() {
        return new RateLimiterProperties();
    }

    /**
     * Configures the rate limiter service.
     * This service handles the rate limiting logic.
     *
     * @return An instance of RateLimiterService.
     */
    @Bean
    @ConditionalOnMissingBean
    public RateLimiterService rateLimiterService() {
        return new RateLimiterService(properties());
    }

    /**
     * Configures the rate limiter interceptor.
     * This interceptor performs rate limiting checks before request processing.
     *
     * @return An instance of RateLimiterInterceptor.
     */
    @Bean
    @ConditionalOnMissingBean
    public RateLimiterInterceptor rateLimiterInterceptor() {
        return new RateLimiterInterceptor(rateLimiterService(), properties());
    }

    /**
     * Configures WebMvcConfigurer to add interceptors.
     * This configuration is only created in a web application environment.
     *
     * @return An instance of WebMvcConfigurer.
     */
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
