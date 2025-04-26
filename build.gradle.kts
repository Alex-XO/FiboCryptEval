plugins {
    kotlin("jvm") version "1.9.10"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin стандартная библиотека
    implementation(kotlin("stdlib"))

    // Логирование через SLF4J + SimpleLogger
    implementation("org.slf4j:slf4j-simple:2.0.9")

    // Библиотека для графиков XChart
    implementation("org.knowm.xchart:xchart:3.8.2")

    // Для тестов
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}