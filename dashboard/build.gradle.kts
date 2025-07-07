plugins {
    java
    id("org.springframework.boot") version "3.5.0"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "tdr.pet"
version = "1.0.0"

repositories {
    mavenCentral()
}

// Define compatible versions
extra["elasticsearchVersion"] = "8.11.4"

dependencies {
    implementation(project(":common-lib"))
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.data:spring-data-jdbc:3.5.0")
    implementation("org.springframework.data:spring-data-commons:3.5.0")
    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch")

    // Use consistent Elasticsearch versions
    implementation("co.elastic.clients:elasticsearch-java:${property("elasticsearchVersion")}")

    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // Jackson for JSON processing
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
}

tasks.test {
    useJUnitPlatform()
}