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
    }

    tasks.test {
        useJUnitPlatform()
    }
}