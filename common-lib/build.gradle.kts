version = "1.0.0"
group = "tdr.pet"

plugins {
    java
}

dependencies {
    implementation("org.springframework.data:spring-data-jpa:3.5.1")
    implementation("org.springframework.data:spring-data-elasticsearch:5.5.1")
    implementation("org.mapstruct:mapstruct:1.6.3")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
}