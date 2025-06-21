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
}

tasks.withType<Jar> {
    archiveBaseName.set("ingestion-app")
}