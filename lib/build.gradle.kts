import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

group = "org.cuongnv.meilisearch"
version = "0.7.2"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.guava:guava:31.1-jre")
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("org.json:json:20220320")

    compileOnly("org.apache.httpcomponents.client5:httpclient5:5.1.3")
    compileOnly("com.squareup.okhttp3:okhttp:4.10.0")

    compileOnly("com.fasterxml.jackson.core:jackson-databind:2.13.2.2")
    compileOnly("jakarta.json.bind:jakarta.json.bind-api:2.0.0")

    implementation("com.auth0:java-jwt:3.19.2")
}
