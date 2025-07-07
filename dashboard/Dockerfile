FROM gradle:8.6-jdk21-alpine as build
LABEL authors="Admin"
WORKDIR /home/gradle/src

# Copy root project files
COPY settings.gradle.kts ./
COPY build.gradle.kts ./
COPY gradle ./gradle

# Copy common-lib and dashboard
COPY common-lib ./common-lib
COPY dashboard ./dashboard

RUN gradle :dashboard:build --no-daemon

FROM eclipse-temurin:21-jre
EXPOSE 5003
RUN mkdir "app"
COPY --from=build /home/gradle/src/dashboard/build/libs/dashboard-*.jar /app/dashboard.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "dashboard.jar"]