package org.tatrman.esotools.api.db

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.tatrman.esotools.api.IntegrationTestBase

class DAOTest :
        IntegrationTestBase({
            // DB is initialized by IntegrationTestBase

            "ProductStockDAO returns correct stock for P001" {
                val dao = ProductStockDAO()
                val stock = dao.getProductStock("P001")
                stock shouldNotBe null
                stock?.currentStock shouldBe 100
                stock?.productType shouldBe "PURCHASED"
                stock?.restockSchedule?.size shouldBe 1
                stock?.restockSchedule?.first()?.sourceId shouldBe "PO-123"
            }

            "OrderStatusDAO returns order by ID" {
                val dao = OrderStatusDAO()
                val orders = dao.getOrders(orderId = "ORD-001")
                orders.size shouldBe 1
                orders.first().status shouldBe "COMPLETED"
                orders.first().totalAmount shouldBe 100.00
            }

            "ReturnStatusDAO returns returns for customer" {
                val dao = ReturnStatusDAO()
                val returns = dao.getReturns("CUST-001")
                returns.size shouldBe 1
                returns.first().returnId shouldBe "RET-001"
            }
        })
