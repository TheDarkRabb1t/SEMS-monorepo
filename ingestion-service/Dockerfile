FROM gradle:8.6-jdk21-alpine as build
LABEL authors="Admin"
WORKDIR /home/gradle/src

COPY ../../settings.gradle.kts ./
COPY ../../build.gradle.kts ./
COPY ../../gradle ./gradle

COPY ../../common-lib ./common-lib
COPY . ./ingestion-service

RUN gradle :ingestion-service:build --no-daemon

FROM eclipse-temurin:21-jre
EXPOSE 5001
RUN mkdir "app"
COPY --from=build /home/gradle/src/ingestion-service/build/libs/ingestion-app-*.jar /app/ingestion-app.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "ingestion-app.jar"]