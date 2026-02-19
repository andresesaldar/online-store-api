FROM gradle:8.14.3-jdk17 AS build
WORKDIR /workspace

COPY inventory ./inventory
COPY common ./common
COPY product/model ./product/model
RUN cd ./inventory && gradle :app:bootJar --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /workspace/inventory/app/build/libs/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
