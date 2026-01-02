package org.tatrman.esotools.api.service

import org.tatrman.esotools.api.db.ProductStockDAO
import org.tatrman.esotools.api.domain.ProductStock

class ProductService(private val dao: ProductStockDAO = ProductStockDAO()) {
    fun getProductStock(productId: String): ProductStock? {
        return dao.getProductStock(productId)
    }
}
