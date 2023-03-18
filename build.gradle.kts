plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "be.tabs_spaces"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.5")

    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(18)
}

application {
    mainClass.set("be.tabs_spaces.transport_tycoon.TransportTycoonApplicationKt")
}
