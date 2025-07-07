plugins {
    java
}

group = "tdr.pet"
java.sourceCompatibility = JavaVersion.VERSION_21

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")

    group = "tdr.pet"

    java.sourceCompatibility = JavaVersion.VERSION_21

    dependencies {
        implementation("org.slf4j:slf4j-api:2.0.17")
        implementation("org.mapstruct:mapstruct:1.6.3")
        compileOnly("org.projectlombok:lombok:1.18.30")

        annotationProcessor("org.projectlombok:lombok:1.18.30")
        annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")

        testImplementation(platform("org.junit:junit-bom:5.13.1"))
        testImplementation("org.junit.jupiter:junit-jupiter-api")
        testImplementation("org.mockito:mockito-core:5.14.2")
        testImplementation("org.mockito:mockito-junit-jupiter:5.14.2")

        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    tasks.test {
        useJUnitPlatform()
    }
}