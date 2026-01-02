plugins {
    alias(libs.plugins.ktor)
    alias(libs.plugins.protobuf)
}

application {
    mainClass.set("org.tatrman.esotools.api.ApplicationKt")
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.server.call.logging)
    implementation(libs.logback.classic)

    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.java.time)
    implementation(libs.hikaricp)
    implementation(libs.mssql.jdbc)
    
    // Shared module contains gRPC stubs/messages
    implementation(project(":shared"))
    
    testImplementation(libs.testcontainers.mssql)
    testImplementation(libs.wiremock)
    testImplementation(libs.ktor.server.test.host)
}


