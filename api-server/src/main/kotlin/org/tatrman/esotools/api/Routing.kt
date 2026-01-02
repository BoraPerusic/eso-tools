package org.tatrman.esotools.api

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.tatrman.esotools.api.service.OrderService
import org.tatrman.esotools.api.service.ProductService
import org.tatrman.esotools.api.service.ReturnService

fun Application.configureRouting() {
    val productService = ProductService()
    val orderService = OrderService()
    val returnService = ReturnService()

    routing {
        get("/") { call.respondText("ESO Tools API Server") }

        route("/products") {
            get("/{id}/stock") {
                val id =
                        call.parameters["id"]
                                ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing ID")
                val stock = productService.getProductStock(id)
                if (stock != null) {
                    call.respond(stock)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }

        route("/orders") {
            get("/{id}") {
                val id =
                        call.parameters["id"]
                                ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing ID")
                val order = orderService.getOrder(id)
                if (order != null) {
                    call.respond(order)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }

        route("/customers") {
            get("/{id}/orders") {
                val id =
                        call.parameters["id"]
                                ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing ID")
                val orders = orderService.getCustomerOrders(id)
                call.respond(orders)
            }

            get("/{id}/returns") {
                val id =
                        call.parameters["id"]
                                ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing ID")
                val returns = returnService.getCustomerReturns(id)
                call.respond(returns)
            }
        }
    }
}
