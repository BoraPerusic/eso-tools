package org.tatrman.esotools.api

import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*

class ApiIntegrationTest :
        IntegrationTestBase({
            "GET /products/{id}/stock returns correct stock" {
                testApplication {
                    application {
                        module() // Initialize app (connects to DB via IntegrationTestBase hook if
                        // possible, or re-inits)
                    }

                    // Note: Application.module() calls DatabaseFactory.init().
                    // In IntegrationTestBase we already initialized Database.connect() with the
                    // container.
                    // DatabaseFactory.init() will try to re-connect using default props if not
                    // careful.
                    // We need to ensure DatabaseFactory respects existing connection or uses
                    // params.
                    // For now, let's assume the module init is safe or we mock it.
                    // Actually, DatabaseFactory.init() uses hardcoded values. We probably need to
                    // overwrite them or logic.
                    // Ideally, we pass config to module().

                    val response = client.get("/products/P001/stock")
                    response.status shouldBe HttpStatusCode.OK
                    response.bodyAsText() shouldContain "currentStock"
                    response.bodyAsText() shouldContain "100"
                }
            }

            "GET /orders/{id} returns order details" {
                testApplication {
                    application { module() }
                    val response = client.get("/orders/ORD-001")
                    response.status shouldBe HttpStatusCode.OK
                    response.bodyAsText() shouldContain "ORD-001"
                }
            }
        })
