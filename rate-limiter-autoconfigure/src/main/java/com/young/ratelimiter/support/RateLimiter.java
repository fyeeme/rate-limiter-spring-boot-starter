package com.young.ratelimiter.support;

import java.util.concurrent.*;

public class RateLimiter {

    private final Semaphore semaphore;
    private final Integer permits;
    private final TimeUnit timeUnit;

    private ScheduledExecutorService scheduledExecutorService;

    private RateLimiter(int permits, TimeUnit timeUnit) {
        this.semaphore = new Semaphore(permits);
        this.permits = permits;
        this.timeUnit = timeUnit;
    }

    public static RateLimiter create(int permits, TimeUnit timeUnit) {
        RateLimiter rateLimiter = new RateLimiter(permits, timeUnit);
        rateLimiter.schedulePermitReplenishment();
        return rateLimiter;
    }

    public boolean tryAcquire() {
        return semaphore.tryAcquire();
    }

    public int availablePermits() {
        return semaphore.availablePermits();
    }

    public void schedulePermitReplenishment() {
        scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleWithFixedDelay(
                () -> semaphore.release(permits - semaphore.availablePermits()), 1, 1, timeUnit);
    }


}
