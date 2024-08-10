package com.young.ratelimiter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties class for rate limiter settings.
 * Uses the @ConfigurationProperties annotation to bind related properties from the configuration file.
 */
@ConfigurationProperties(prefix = RateLimiterProperties.PREFIX)
public class RateLimiterProperties {
    // Prefix for configuration properties
    public static final String PREFIX = "spring.rate-limiter";

    // Enable or disable the rate limiter
    private Boolean enable;

    // Limit count for the rate limiter
    private Integer limit;

    // Whether to show rate limiting information in HTTP response headers
    private Boolean showHeader;

    /**
     * Gets the enable status of the rate limiter.
     *
     * @return The enable status of the rate limiter.
     */
    public Boolean getEnable() {
        return enable;
    }

    /**
     * Sets the enable status of the rate limiter.
     *
     * @param enable The enable status of the rate limiter.
     */
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    /**
     * Gets the limit count for the rate limiter.
     *
     * @return The limit count for the rate limiter.
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * Sets the limit count for the rate limiter.
     *
     * @param limit The limit count for the rate limiter.
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * Gets whether to show rate limiting information in HTTP response headers.
     *
     * @return Whether to show rate limiting information.
     */
    public Boolean getShowHeader() {
        return showHeader;
    }

    /**
     * Sets whether to show rate limiting information in HTTP response headers.
     *
     * @param showHeader Whether to show rate limiting information.
     */
    public void setShowHeader(Boolean showHeader) {
        this.showHeader = showHeader;
    }
}
