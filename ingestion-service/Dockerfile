FROM gradle:8.6-jdk21-alpine as build
LABEL authors="Admin"
WORKDIR /home/gradle/src

# Copy root project files
COPY settings.gradle.kts ./
COPY build.gradle.kts ./
COPY gradle ./gradle

# Copy common-lib and ingestion-service
COPY common-lib ./common-lib
COPY ingestion-service ./ingestion-service

RUN gradle :ingestion-service:build --no-daemon

FROM eclipse-temurin:21-jre
EXPOSE 5001
RUN mkdir "app"
COPY --from=build /home/gradle/src/ingestion-service/build/libs/ingestion-app-*.jar /app/ingestion-app.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "ingestion-app.jar"]