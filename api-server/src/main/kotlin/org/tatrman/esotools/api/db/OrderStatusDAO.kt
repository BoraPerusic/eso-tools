package org.tatrman.esotools.api.db

import java.sql.ResultSet
import org.jetbrains.exposed.sql.transactions.transaction
import org.tatrman.esotools.api.domain.OrderStatus

class OrderStatusDAO {
    fun getOrders(orderId: String? = null, customerId: String? = null): List<OrderStatus> {
        if (orderId == null && customerId == null) return emptyList()

        return transaction {
            val params = mutableMapOf<String, Any?>()
            if (orderId != null) params["OrderId"] = orderId
            if (customerId != null) params["CustomerId"] = customerId

            StoredProcedureExecutor.execute(this, "sp_GetOrderStatus", params) { rs -> mapRow(rs) }
        }
    }

    private fun mapRow(rs: ResultSet): OrderStatus {
        return OrderStatus(
                orderId = rs.getString("OrderId"),
                customerId = rs.getString("CustomerId"),
                status = rs.getString("Status"),
                paymentStatus = rs.getString("PaymentStatus"),
                deliveryDate = rs.getDate("DeliveryDate")?.toLocalDate(),
                totalAmount = rs.getDouble("TotalAmount")
        )
    }
}
