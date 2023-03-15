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

Download the repo, and install to local maven

```sh
cd rate-limier-autoconfigure
# 发布到 maven 本地
gradle publishToMavenLocal
cd ..
gradle publishToMavenLocal

```

After doing that. add deps to your project

maven:

```xml
 <dependency>
  <groupId>com.young</groupId>
  <artifactId>rate-limiter-spring-boot-starter</artifactId>
  <version>0.0.1</version>
</dependency>
```

gradle:

```groovy
implementation 'com.young:rate-limiter-spring-boot-starter:0.0.1'
```

Or fork this repo, and publish to your own maven

### example

start `rate-limiter-example` default port is `8080` and visit `http://localhost:8080/users` for several times. then api will return too many request
