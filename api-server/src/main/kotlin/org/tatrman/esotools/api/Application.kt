package org.tatrman.esotools.api

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.tatrman.esotools.api.db.DatabaseFactory

fun main() {
    embeddedServer(Netty, port = 8081, host = "0.0.0.0", module = Application::module)
            .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(
                Json {
                    prettyPrint = true
                    isLenient = true
                }
        )
    }

    // Initialize Database
    try {
        DatabaseFactory.init()
    } catch (e: Exception) {
        log.error("Failed to initialize database: ${e.message}")
    }

    configureRouting()

    // Start gRPC Server
    io.grpc.ServerBuilder.forPort(50051)
            .addService(org.tatrman.esotools.api.grpc.EsoServiceGrpcImpl())
            .build()
            .start()
}
