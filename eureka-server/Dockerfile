FROM gradle:8.14.3-jdk17 AS build
WORKDIR /workspace

COPY eureka-server ./eureka-server
RUN cd ./eureka-server && gradle :app:bootJar --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /workspace/eureka-server/app/build/libs/*.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
