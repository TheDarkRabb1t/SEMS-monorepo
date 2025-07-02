version = "1.0.0"

plugins {
    java
    id("org.springframework.boot") version "3.5.0"
    id("io.spring.dependency-management") version "1.1.7"
}

dependencies {
    implementation(project(":common-lib"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-amqp")

    // OAuth2 Resource Server dependencies
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    // For HTTP client to call authorization server
    implementation("org.springframework.boot:spring-boot-starter-webflux")
}

tasks.withType<Jar> {
    archiveBaseName.set("ingestion-app")
}