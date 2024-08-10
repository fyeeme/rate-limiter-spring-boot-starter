package com.young.ratelimiter.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * Marks the maximum invocation frequency of a method.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RateLimit {

    /**
     * Gets the rate limit count.
     *
     * @return The rate limit count
     */
    int limit() default 5;

    /**
     * Gets the time unit.
     *
     * @return The time unit
     */
    TimeUnit timeUnit() default TimeUnit.MINUTES;
}
