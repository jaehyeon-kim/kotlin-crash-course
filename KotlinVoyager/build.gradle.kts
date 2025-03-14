plugins {
    kotlin("jvm") version "1.9.21"
    id("org.jetbrains.dokka") version "2.0.0"
    application
}

group = "me.jaehyeon"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
    implementation("ch.qos.logback:logback-classic:1.5.17")
    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk-jvm:1.13.10")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("me.jaehyeon.voyager.MainKt")
}
