version = "1.0.0"
group = "tdr.pet"

plugins {
    java
    id("org.springframework.boot") version "3.5.0"
    id("io.spring.dependency-management") version "1.1.7"
}

dependencies {
    implementation("org.mapstruct:mapstruct:1.6.3")
    implementation("org.springframework.boot:spring-boot-starter-web")
    //auth
    implementation("org.springframework.boot:spring-boot-starter-security:3.5.0")
    implementation("org.springframework.security:spring-security-oauth2-authorization-server:1.5.0")
    implementation("com.nimbusds:oauth2-oidc-sdk")
    //data
    implementation("org.springframework.data:spring-data-jdbc:3.5.1")
    implementation("org.postgresql:postgresql:42.7.7")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
}

tasks.withType<Jar> {
    archiveBaseName.set("authorization-server")
}