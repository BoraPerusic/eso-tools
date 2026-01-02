package org.tatrman.esotools.api.db

import java.sql.ResultSet
import org.jetbrains.exposed.sql.transactions.transaction
import org.tatrman.esotools.api.domain.ReturnStatus

class ReturnStatusDAO {
    fun getReturns(customerId: String): List<ReturnStatus> {
        return transaction {
            StoredProcedureExecutor.execute(
                    this,
                    "sp_GetReturnStatus",
                    mapOf("CustomerId" to customerId)
            ) { rs -> mapRow(rs) }
        }
    }

    private fun mapRow(rs: ResultSet): ReturnStatus {
        return ReturnStatus(
                returnId = rs.getString("ReturnId"),
                originalOrderId = rs.getString("OriginalOrderId"),
                status = rs.getString("Status"),
                resolutionType = rs.getString("ResolutionType"),
                plannedActionDate = rs.getDate("PlannedActionDate")?.toLocalDate()
        )
    }
}
