plugins {
    java
    id("org.springframework.boot") version "3.5.0"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "tdr.pet"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common-lib"))
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.data:spring-data-jdbc:3.5.0")
    implementation("org.springframework.data:spring-data-commons:3.5.0")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.data:spring-data-elasticsearch:5.5.1")
}

tasks.test {
    useJUnitPlatform()
}