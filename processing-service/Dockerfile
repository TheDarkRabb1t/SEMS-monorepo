FROM gradle:8.6-jdk21-alpine as build
LABEL authors="Admin"
WORKDIR /home/gradle/src

COPY ../../settings.gradle.kts ./
COPY ../../build.gradle.kts ./
COPY ../../gradle ./gradle

COPY ../../common-lib ./common-lib
COPY . ./processing-service

RUN gradle :processing-service:build --no-daemon

FROM eclipse-temurin:21-jre
EXPOSE 5002
RUN mkdir "app"
COPY --from=build /home/gradle/src/processing-service/build/libs/processing-app-*.jar /app/processing-app.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "processing-app.jar"]