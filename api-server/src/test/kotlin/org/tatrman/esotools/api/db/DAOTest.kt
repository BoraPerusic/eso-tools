package org.tatrman.esotools.api.db

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class DAOTest :
        StringSpec({
            // We assume the DB is running via docker-compose up
            // In a real CI, we'd use Testcontainers, but per plan we use local docker

            // We only run this if we can connect (simple check) because CI might fail otherwise
            // ideally enabledConfig = ...

            beforeSpec {
                try {
                    DatabaseFactory.init()
                } catch (e: Exception) {
                    println("Skipping DB tests: ${e.message}")
                }
            }

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
