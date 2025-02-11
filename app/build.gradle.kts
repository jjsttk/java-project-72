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
    testImplementation(platform("org.junit:junit-bom:5.11.2"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.1")
    testImplementation("org.assertj:assertj-core:3.26.3")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")

    implementation("com.mashape.unirest:unirest-java:1.4.9")

    implementation ("org.jsoup:jsoup:1.18.3")

    implementation("io.javalin:javalin:6.4.0")
    implementation ("io.javalin:javalin-rendering:6.4.0")
    implementation ("io.javalin:javalin-bundle:6.4.0")
    implementation("gg.jte:jte:3.1.16")

    implementation("org.slf4j:slf4j-simple:2.0.16")

    implementation("com.h2database:h2:2.3.232")
    implementation("org.postgresql:postgresql:42.7.5")
    implementation("com.zaxxer:HikariCP:5.1.0")
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