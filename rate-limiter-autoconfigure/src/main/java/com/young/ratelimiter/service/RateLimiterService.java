package com.young.ratelimiter.service;

import com.young.ratelimiter.config.RateLimiterProperties;
import com.young.ratelimiter.support.RateLimiter;
import com.young.ratelimiter.support.RateLimiterKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class RateLimiterService {
    public static final Logger log = LoggerFactory.getLogger(RateLimiterService.class);
    private final RateLimiterProperties rateLimiterProperties;

    private final Map<String, RateLimiter> limiterMap = new ConcurrentHashMap<>();
    private ReentrantLock lock = new ReentrantLock();

    public RateLimiterService(RateLimiterProperties rateLimiterProperties) {
        this.rateLimiterProperties = rateLimiterProperties;
    }

    public boolean tryAcquire(RateLimiterKey rateLimiterKey) {
        return getRateLimiter(rateLimiterKey).tryAcquire();
    }

    public int availablePermits(RateLimiterKey rateLimiterKey) {
        return getRateLimiter(rateLimiterKey).availablePermits();
    }

    private RateLimiter getRateLimiter(RateLimiterKey rateLimiterKey) {
        log.info("build rateLimiter: {}", rateLimiterProperties.getLimit());
        RateLimiter rateLimiter;
        String cacheKey = rateLimiterKey.getCacheKey();
        if (limiterMap.containsKey(cacheKey)) {
            rateLimiter = limiterMap.get(cacheKey);
        } else {
            lock.lock();
            try {
                if (limiterMap.containsKey(cacheKey)) {
                    rateLimiter = limiterMap.get(cacheKey);
                } else {
                    rateLimiter = RateLimiter.create(rateLimiterKey.getLimit(), rateLimiterKey.getTimeUnit());
                }
                limiterMap.put(cacheKey, rateLimiter);
            } finally {
                lock.unlock();
            }
        }
        return rateLimiter;
    }
}
