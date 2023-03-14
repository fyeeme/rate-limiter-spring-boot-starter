# RateLimiterSpringBootStarter

### overview

The annotation-based approach implements spring boot rate limits, based on ip,

```java
  @RateLimit() // default config, 30 per minute
  @RateLimit(limit = 5) // 50 per minute
  @RateLimit(limit = 5, timeUnit = TimeUnit.HOURS) // 5 per hour
```

```yml
spring.rate-limiter.enable # if enable
spring.rate-limiter.showHeader # if show headers on response
```

### how to use

download the repo, and install to local maven

```sh
mvn clean install
```

after doing this. add deps to your project

```xml
 <dependency>
  <groupId>com.young</groupId>
  <artifactId>rate-limiter-spring-boot-starter</artifactId>
  <version>0.0.1</version>
</dependency>
```

```groovy
implementation 'com.young:rate-limiter-spring-boot-starter:0.0.1'
```
