package org.tatrman.esotools.mcp

import io.modelcontextprotocol.kotlin.sdk.types.CallToolRequest
import io.modelcontextprotocol.kotlin.sdk.types.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.types.TextContent
import io.modelcontextprotocol.kotlin.sdk.types.Tool
import io.modelcontextprotocol.kotlin.sdk.types.ToolSchema
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonObject
import org.tatrman.esotools.mcp.client.EsoApiClient

@kotlinx.serialization.Serializable data class ProductStockInput(val productId: String)

@kotlinx.serialization.Serializable data class OrderStatusInput(val orderId: String)

@kotlinx.serialization.Serializable data class ReturnStatusInput(val customerId: String)

class Tools(private val client: EsoApiClient) {

    val listProductStockTool =
            Tool(
                    name = "get_product_stock",
                    description = "Get stock level and restock schedule for a product",
                    inputSchema =
                            ToolSchema(
                                    properties =
                                            buildJsonObject {
                                                putJsonObject("productId") {
                                                    put("type", "string")
                                                    put(
                                                            "description",
                                                            "The Product ID (e.g., P001)"
                                                    )
                                                }
                                            },
                                    required = listOf("productId")
                            )
            )

    suspend fun productStockCallback(request: CallToolRequest): CallToolResult {
        // request.params.arguments is JsonObject?
        // Need to check specific structure of CallToolRequest. Use safe access for now.
        // Assuming request.params.arguments is the arguments object.
        val args =
                request.params.arguments
                        ?: return CallToolResult(
                                content = listOf(TextContent(text = "Missing arguments")),
                                isError = true
                        )

        try {
            val input = Json.decodeFromJsonElement<ProductStockInput>(args)
            val response = client.getProductStock(input.productId)
            val restockInfo =
                    response.restockScheduleList.joinToString(", ") {
                        "${it.date} (+${it.quantity})"
                    }
            return CallToolResult(
                    content =
                            listOf(
                                    TextContent(
                                            text =
                                                    "Stock: ${response.currentStock}. Restock Schedule: $restockInfo"
                                    )
                            )
            )
        } catch (e: Exception) {
            return CallToolResult(
                    content = listOf(TextContent(text = "Failed to fetch stock: ${e.message}")),
                    isError = true
            )
        }
    }

    val checkOrderStatusTool =
            Tool(
                    name = "check_order_status",
                    description = "Check the status of an order",
                    inputSchema =
                            ToolSchema(
                                    properties =
                                            buildJsonObject {
                                                putJsonObject("orderId") {
                                                    put("type", "string")
                                                    put("description", "The Order ID")
                                                }
                                            },
                                    required = listOf("orderId")
                            )
            )

    suspend fun orderStatusCallback(request: CallToolRequest): CallToolResult {
        val args =
                request.params.arguments
                        ?: return CallToolResult(
                                content = listOf(TextContent(text = "Missing arguments")),
                                isError = true
                        )
        try {
            val input = Json.decodeFromJsonElement<OrderStatusInput>(args)
            val response = client.getOrderStatus(input.orderId)
            return CallToolResult(
                    content =
                            listOf(
                                    TextContent(
                                            text =
                                                    "Order Status: ${response.status}. Delivery: ${response.deliveryDate}"
                                    )
                            )
            )
        } catch (e: Exception) {
            return CallToolResult(
                    content = listOf(TextContent(text = "Failed to fetch order: ${e.message}")),
                    isError = true
            )
        }
    }

    val checkReturnStatusTool =
            Tool(
                    name = "check_return_status",
                    description = "Check return status for a customer",
                    inputSchema =
                            ToolSchema(
                                    properties =
                                            buildJsonObject {
                                                putJsonObject("customerId") {
                                                    put("type", "string")
                                                    put("description", "The Customer ID")
                                                }
                                            },
                                    required = listOf("customerId")
                            )
            )

    suspend fun returnStatusCallback(request: CallToolRequest): CallToolResult {
        val args =
                request.params.arguments
                        ?: return CallToolResult(
                                content = listOf(TextContent(text = "Missing arguments")),
                                isError = true
                        )
        try {
            val input = Json.decodeFromJsonElement<ReturnStatusInput>(args)
            val response = client.getReturnStatus(input.customerId)
            val returnsInfo =
                    response.returnsList.joinToString("\n") {
                        "- Return ${it.returnId}: ${it.status} (${it.resolutionType})"
                    }
            return CallToolResult(
                    content =
                            listOf(
                                    TextContent(
                                            text = "Returns for ${input.customerId}:\n$returnsInfo"
                                    )
                            )
            )
        } catch (e: Exception) {
            return CallToolResult(
                    content = listOf(TextContent(text = "Failed to fetch returns: ${e.message}")),
                    isError = true
            )
        }
    }
}
