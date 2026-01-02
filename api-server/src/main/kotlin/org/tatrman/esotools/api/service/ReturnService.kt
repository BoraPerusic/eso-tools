package org.tatrman.esotools.api.service

import org.tatrman.esotools.api.db.ReturnStatusDAO
import org.tatrman.esotools.api.domain.ReturnStatus

class ReturnService(private val dao: ReturnStatusDAO = ReturnStatusDAO()) {
    fun getCustomerReturns(customerId: String): List<ReturnStatus> {
        return dao.getReturns(customerId)
    }
}
