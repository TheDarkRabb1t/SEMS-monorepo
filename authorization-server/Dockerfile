FROM gradle:8.6-jdk21-alpine as build
LABEL authors="Admin"
WORKDIR /home/gradle/src

# Copy root project files
COPY settings.gradle.kts ./
COPY build.gradle.kts ./
COPY gradle ./gradle

# Copy common-lib and authorization-server
COPY common-lib ./common-lib
COPY authorization-server ./authorization-server

RUN gradle :authorization-server:build --no-daemon

FROM eclipse-temurin:21-jre
EXPOSE 5001
RUN mkdir "app"
COPY --from=build /home/gradle/src/authorization-server/build/libs/authorization-app-*.jar /app/authorization-app.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "authorization-app.jar"]