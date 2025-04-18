plugins {
    kotlin("jvm") version "2.0.21"
    application
}

group = "me.jaehyeon"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("me.jaehyeon.f1app.MainKt")
}
