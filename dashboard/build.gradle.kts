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

dependencies {
    implementation(project(":common-lib"))
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.data:spring-data-jdbc:3.5.0")
    implementation("org.springframework.data:spring-data-commons:3.5.0")
    implementation("org.springframework.boot:spring-boot-starter-security")


    implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch")
    implementation("co.elastic.clients:elasticsearch-java:9.0.3")
    implementation("org.elasticsearch:elasticsearch:9.0.3")

    // Jackson for JSON processing
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
}

tasks.test {
    useJUnitPlatform()
}