# RateLimiterSpringBootStarter
[![Java CI with Maven](https://github.com/fyeeme/rate-limiter-spring-boot-starter/actions/workflows/maven.yml/badge.svg)](https://github.com/fyeeme/rate-limiter-spring-boot-starter/actions/workflows/maven.yml)
[![](https://jitpack.io/v/fyeeme/rate-limiter-spring-boot-starter.svg)](https://jitpack.io/#fyeeme/rate-limiter-spring-boot-starter)

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

### TODO 
- [ ] support custom key base on spl
- [ ] support redis for distributed Services
### how to use

#### 1. maven local repo 

Download the repo, and install to local maven

```sh
mvn clean install
```

After doing that. add deps to your project

maven:

```xml
 <dependency>
  <groupId>com.young</groupId>
  <artifactId>rate-limiter-spring-boot-starter</artifactId>
  <version>1.0.0</version>
</dependency>
```

gradle:

```groovy
implementation 'com.young:rate-limiter-spring-boot-starter:1.0.0'
```

Or fork this repo, and publish to your own maven

#### 2. jitpack

use maven 
```xml 
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

```xml 
<dependency>
    <groupId>com.github.fyeeme</groupId>
    <artifactId>rate-limiter-spring-boot-starter</artifactId>
    <!--替换成真实的地址，比如： 1.0.3-->
    <version>Tag</version>
    
</dependency>
```

use gradle 

```gradle 
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

```gradle 
dependencies {
      implementation 'com.github.fyeeme:rate-limiter-spring-boot-starter:Tag'
}
```


### Run locally

with docker 

```shell
docker build --platform linux/amd64 -t rate-limiter .
docker run --rm -p 8011:8080 rate-limiter
```
start `rate-limiter-example` default port is `8080` and visit `http://localhost:8080/users` for several times. then api will return too many request

### Visit demo 


visit [https://rate-limiter-spring-boot-starter.onrender.com/users](https://rate-limiter-spring-boot-starter.onrender.com/users) , Refresh the link address. You can access it up to 5 times per minute. If you exceed 5 times, a 429 exception will be thrown. You can continue accessing it after waiting for one minute.

also the response headers will also expose parameters

```shell
x-rate-limit-remaining:4
x-ratelimit-limit:5
```
