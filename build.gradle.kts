plugins {
    java
}

group = "tdr.pet"
version = "1.0.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_21

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")

    group = "tdr.pet"
    version = "1.0.0-SNAPSHOT"

    java.sourceCompatibility = JavaVersion.VERSION_21

    dependencies {
        implementation("org.slf4j:slf4j-api:2.0.17")
        compileOnly("org.projectlombok:lombok:1.18.30")
        annotationProcessor("org.projectlombok:lombok:1.18.30")

        testImplementation(platform("org.junit:junit-bom:5.13.1"))
        testImplementation("org.junit.jupiter:junit-jupiter-api")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    tasks.test {
        useJUnitPlatform()
    }
}