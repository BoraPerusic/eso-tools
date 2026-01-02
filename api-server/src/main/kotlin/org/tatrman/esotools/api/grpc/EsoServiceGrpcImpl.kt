package org.tatrman.esotools.api.grpc

import io.grpc.Status
import io.grpc.StatusException
import org.tatrman.esotools.api.service.OrderService
import org.tatrman.esotools.api.service.ProductService
import org.tatrman.esotools.api.service.ReturnService

class EsoServiceGrpcImpl(
        private val productService: ProductService = ProductService(),
        private val orderService: OrderService = OrderService(),
        private val returnService: ReturnService = ReturnService()
) : EsoServiceGrpcKt.EsoServiceCoroutineImplBase() {

    override suspend fun getProductStock(request: ProductStockRequest): ProductStockResponse {
        val stock =
                productService.getProductStock(request.productId)
                        ?: throw StatusException(
                                Status.NOT_FOUND.withDescription("Product not found")
                        )

        return productStockResponse {
            productId = stock.productId
            currentStock = stock.currentStock
            productType = stock.productType
            restockSchedule.addAll(
                    stock.restockSchedule.map { rs ->
                        restockItem {
                            date = rs.date.toString()
                            quantity = rs.quantity
                            sourceId = rs.sourceId
                            type = rs.type
                        }
                    }
            )
        }
    }

    override suspend fun getOrderStatus(request: OrderStatusRequest): OrderStatusResponse {
        val order =
                orderService.getOrder(request.orderId)
                        ?: throw StatusException(
                                Status.NOT_FOUND.withDescription("Order not found")
                        )

        return orderStatusResponse {
            orderId = order.orderId
            status = order.status
            paymentStatus = order.paymentStatus
            deliveryDate = order.deliveryDate?.toString() ?: ""
            totalAmount = order.totalAmount
        }
    }

    override suspend fun getReturnStatus(request: ReturnStatusRequest): ReturnStatusResponse {
        val returns = returnService.getCustomerReturns(request.customerId)

        return returnStatusResponse {
            this.returns.addAll(
                    returns.map { r ->
                        returnItem {
                            returnId = r.returnId
                            originalOrderId = r.originalOrderId
                            status = r.status
                            resolutionType = r.resolutionType
                            plannedActionDate = r.plannedActionDate?.toString() ?: ""
                        }
                    }
            )
        }
    }
}
