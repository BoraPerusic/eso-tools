package org.tatrman.esotools.api.domain

import java.time.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class ProductStock(
        val productId: String,
        val currentStock: Int,
        val productType: String, // PURCHASED | MANUFACTURED
        val restockSchedule: List<RestockItem>
)

@Serializable
data class RestockItem(
        @Serializable(with = LocalDateSerializer::class) val date: LocalDate,
        val quantity: Int,
        val sourceId: String,
        val type: String // PO | MO
)
