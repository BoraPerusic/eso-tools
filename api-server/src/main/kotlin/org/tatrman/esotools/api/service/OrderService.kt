package org.tatrman.esotools.api.service

import org.tatrman.esotools.api.db.OrderStatusDAO
import org.tatrman.esotools.api.domain.OrderStatus

class OrderService(private val dao: OrderStatusDAO = OrderStatusDAO()) {
    fun getOrder(orderId: String): OrderStatus? {
        return dao.getOrders(orderId = orderId).firstOrNull()
    }

    fun getCustomerOrders(customerId: String): List<OrderStatus> {
        return dao.getOrders(customerId = customerId)
    }
}
