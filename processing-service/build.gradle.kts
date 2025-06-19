plugins {
    id("org.springframework.boot") version "3.5.0"
    id("io.spring.dependency-management") version "1.1.7"
}

dependencies {
    implementation(project(":common-lib"))
    implementation("com.maxmind.geoip2:geoip2:4.3.1")
    implementation("org.springframework.boot:spring-boot-starter-amqp")
}