# RateLimiterSpringBootStarter
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
  <version>0.0.1</version>
</dependency>
```

gradle:

```groovy
implementation 'com.young:rate-limiter-spring-boot-starter:0.0.1'
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


### example

start `rate-limiter-example` default port is `8080` and visit `http://localhost:8080/users` for several times. then api will return too many request
