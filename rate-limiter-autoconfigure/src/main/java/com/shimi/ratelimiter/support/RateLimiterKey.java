package com.shimi.ratelimiter.support;

import com.shimi.ratelimiter.annotation.RateLimit;
import org.springframework.web.method.HandlerMethod;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class RateLimiterKey {
    private int limit;
    private TimeUnit timeUnit;
    private String clazz;
    private String method;
    private String cacheKey;

    private RateLimiterKey(int limit, TimeUnit timeUnit, String clazz, String method, String cacheKey) {
        this.limit = limit;
        this.timeUnit = timeUnit;
        this.clazz = clazz;
        this.method = method;
        this.cacheKey = cacheKey;
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RateLimiterKey that = (RateLimiterKey) o;
        return limit == that.limit &&
                timeUnit == that.timeUnit &&
                cacheKey.equals(that.cacheKey);
    }


    @Override
    public int hashCode() {
        return Objects.hash(limit, timeUnit, cacheKey);
    }

    @Override
    public String toString() {
        return "RateLimiterKey{" +
                "method='" + method + '\'' +
                ", cacheKey='" + cacheKey + '\'' +
                '}';
    }

    public static class Builder {
        private int limit;
        private TimeUnit timeUnit;
        private String method;
        private String clazz;
        private String requestUri;
        private String remoteAddr;
        private String requestMethod;

        public Builder rateLimit(RateLimit rateLimit) {
            this.limit = rateLimit.limit();
            this.timeUnit = rateLimit.timeUnit();
            return this;
        }

        public Builder requestUri(String requestUri) {
            this.requestUri = requestUri;
            return this;
        }

        public Builder remoteAddr(String remoteAddr) {
            this.remoteAddr = remoteAddr;
            return this;
        }

        public Builder handlerMethod(HandlerMethod handlerMethod) {
            this.clazz = handlerMethod.getBeanType().getName();
            this.method = handlerMethod.getMethod().getName();
            return this;
        }

        public RateLimiterKey build() {
            return new RateLimiterKey(limit, timeUnit, clazz, method, getCacheKey());
        }

        private String getCacheKey() {
            return remoteAddr + "#" + requestMethod + "#" + requestUri;
        }

        public Builder requestMethod(String method) {
            this.requestMethod = method;
            return this;
        }
    }
}
