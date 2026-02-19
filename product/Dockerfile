FROM gradle:8.14.3-jdk17 AS build
WORKDIR /workspace

COPY product ./product
COPY common ./common
RUN cd ./product && gradle :app:bootJar --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /workspace/product/app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
