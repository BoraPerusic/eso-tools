package org.tatrman.esotools.api.db

import org.jetbrains.exposed.sql.transactions.transaction
import org.tatrman.esotools.api.domain.ProductStock
import org.tatrman.esotools.api.domain.RestockItem

class ProductStockDAO {
    fun getProductStock(productId: String): ProductStock? {
        return transaction {
            var stockInfo: Pair<Int, String>? = null
            val schedule = mutableListOf<RestockItem>()

            StoredProcedureExecutor.executeMulti(
                    this,
                    "sp_GetProductStock",
                    mapOf("ProductId" to productId)
            ) { index, rs ->
                if (index == 0) {
                    // Result Set 1: Stock Overview
                    while (rs.next()) {
                        stockInfo = rs.getInt("CurrentStock") to rs.getString("ProductType")
                    }
                } else if (index == 1) {
                    // Result Set 2: Restock Schedule
                    while (rs.next()) {
                        schedule.add(
                                RestockItem(
                                        date = rs.getDate("Date").toLocalDate(),
                                        quantity = rs.getInt("Quantity"),
                                        sourceId = rs.getString("SourceId"),
                                        type = rs.getString("Type")
                                )
                        )
                    }
                }
            }

            stockInfo?.let { (qty, type) ->
                ProductStock(
                        productId = productId,
                        currentStock = qty,
                        productType = type,
                        restockSchedule = schedule
                )
            }
        }
    }
}
