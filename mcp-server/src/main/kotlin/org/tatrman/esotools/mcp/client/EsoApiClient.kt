package org.tatrman.esotools.mcp.client

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import java.io.Closeable
import java.util.concurrent.TimeUnit
import org.tatrman.esotools.grpc.*

class EsoApiClient(
        host: String = System.getenv("API_SERVER_HOST") ?: "localhost",
        port: Int = System.getenv("API_SERVER_PORT")?.toIntOrNull() ?: 50051
) : Closeable {

    private val channel: ManagedChannel =
            ManagedChannelBuilder.forAddress(host, port).usePlaintext().build()

    private val stub: EsoServiceGrpcKt.EsoServiceCoroutineStub =
            EsoServiceGrpcKt.EsoServiceCoroutineStub(channel)

    suspend fun getProductStock(productId: String): ProductStockResponse {
        val request = productStockRequest { this.productId = productId }
        return stub.getProductStock(request)
    }

    suspend fun getOrderStatus(orderId: String): OrderStatusResponse {
        val request = orderStatusRequest { this.orderId = orderId }
        return stub.getOrderStatus(request)
    }

    suspend fun getReturnStatus(customerId: String): ReturnStatusResponse {
        val request = returnStatusRequest { this.customerId = customerId }
        return stub.getReturnStatus(request)
    }

    override fun close() {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
}
