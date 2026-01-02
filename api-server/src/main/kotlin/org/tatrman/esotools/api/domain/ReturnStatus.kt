package org.tatrman.esotools.api.domain

import java.time.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class ReturnStatus(
        val returnId: String,
        val originalOrderId: String,
        val status: String,
        val resolutionType: String,
        @Serializable(with = LocalDateSerializer::class) val plannedActionDate: LocalDate?
)
