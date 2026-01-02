package org.tatrman.esotools.api.domain

import java.time.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class OrderStatus(
        val orderId: String,
        val customerId: String,
        val status: String, // OPEN, COMPLETED, BASKET, CANCELLED
        val paymentStatus: String, // PAID, PENDING, FAILED
        @Serializable(with = LocalDateSerializer::class) val deliveryDate: LocalDate?,
        val totalAmount: Double // Using Double for DECIMAL
)
