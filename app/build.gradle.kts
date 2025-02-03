import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    application
    checkstyle
    jacoco
    id("io.freefair.lombok") version "8.6"
    id("com.github.ben-manes.versions") version "0.51.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // tests
    testImplementation(platform("org.junit:junit-bom:5.10.3"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.3")
    testImplementation("org.assertj:assertj-core:3.26.3")

    // Javalin
    implementation("io.javalin:javalin:6.4.0")
    implementation ("io.javalin:javalin-rendering:6.4.0")
    // Javalin tests
    implementation ("io.javalin:javalin-bundle:6.4.0")
    // template
    implementation("gg.jte:jte:3.1.16")

    // logger
    implementation("org.slf4j:slf4j-simple:2.0.13")

    // h2 db
    implementation("com.h2database:h2:2.2.224")

    // Postgres driver
    implementation("org.postgresql:postgresql:42.7.5")

    // hikari
    implementation("com.zaxxer:HikariCP:5.1.0")

    // jackson core
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
    }
}

tasks.test {
    useJUnitPlatform()
    // https://technology.lastminute.com/junit5-kotlin-and-gradle-dsl/
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        events = mutableSetOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
        // showStackTraces = true
        // showCauses = true
        showStandardStreams = true
    }
}

application {
    mainClass = "hexlet.code.App"
}