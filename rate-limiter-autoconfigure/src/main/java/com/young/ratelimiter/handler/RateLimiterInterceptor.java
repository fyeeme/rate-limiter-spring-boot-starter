package com.young.ratelimiter.handler;

import com.young.ratelimiter.annotation.RateLimit;
import com.young.ratelimiter.config.RateLimiterProperties;
import com.young.ratelimiter.service.RateLimiterService;
import com.young.ratelimiter.support.RateLimiterKey;
import com.young.ratelimiter.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class RateLimiterInterceptor implements HandlerInterceptor {
    public static final Logger log = LoggerFactory.getLogger(RateLimiterInterceptor.class);

    private final RateLimiterProperties properties;
    private final RateLimiterService rateLimiterService;

    public RateLimiterInterceptor(RateLimiterService rateLimiterService, RateLimiterProperties properties) {
        this.rateLimiterService = rateLimiterService;
        this.properties = properties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        RateLimit annotation = handlerMethod.getMethod().getAnnotation(RateLimit.class);
        if (Objects.isNull(annotation)) {
            return true;
        }

        RateLimiterKey rateLimiterKey = RateLimiterKey.builder()
                .rateLimit(annotation)
                .handlerMethod(handlerMethod)
                .requestMethod(request.getMethod())
                .requestUri(request.getRequestURI())
                .remoteAddr(IpUtil.getIpAddr(request))
                .build();

        log.trace("build rateLimiter key: {}", rateLimiterKey);

        boolean tryAcquire = rateLimiterService.tryAcquire(rateLimiterKey);

        if (!tryAcquire) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            log.warn("Too many request: {}", rateLimiterKey);
        }
        if (Objects.equals(properties.getShowHeader(), Boolean.TRUE)) {
            response.addHeader("X-RateLimit-Limit", String.valueOf(annotation.limit()));
            response.addHeader("X-Rate-Limit-Remaining", String.valueOf(rateLimiterService.availablePermits(rateLimiterKey)));
        }
        return tryAcquire;
    }

//    private User getCurrentUser() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        if (auth != null && auth.getPrincipal() instanceof User) {
//            return (User) auth.getPrincipal();
//        } else {
//            return new User();
//        }
//    }

}
