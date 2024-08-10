# 第一阶段：构建应用程序
FROM maven:3.8.4-openjdk-17-slim AS build

WORKDIR /app
COPY . .

RUN mvn clean package -pl rate-limiter-example  -DskipTests

# 第二阶段：运行应用程序
FROM openjdk:17-jdk-alpine

# 从第一阶段中复制生成的 jar 文件到当前阶段
COPY --from=build /app/rate-limiter-example/target/*.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]