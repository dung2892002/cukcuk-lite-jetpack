package com.example.domain.usecase.invoice

import com.example.domain.model.InventorySelect
import com.example.domain.repository.InvoiceRepository
import java.util.UUID

class GetInventorySelectUseCase (
    private val repository: InvoiceRepository
) {
    suspend operator fun invoke(invoiceId: UUID?): List<InventorySelect> {
        val inventories = repository.getAllInventoryInactive()
        val invoiceDetails = if (invoiceId != null) repository.getListInvoicesDetail(invoiceId) else emptyList()

        // Map: InventoryID → InvoiceDetail
        val detailMap = invoiceDetails.associateBy { it.InventoryID }

        return inventories
            .map { inventory ->
                val detail = detailMap[inventory.InventoryID]
                val quantity = detail?.Quantity ?: 0.0
                val sortOrder = detail?.SortOrder ?: Int.MAX_VALUE
                val hasDetail = detail != null

                Triple(InventorySelect(inventory, quantity), sortOrder, hasDetail)
            }
            .sortedWith(compareBy({ it.second }, { !it.third })) // sortOrder, rồi đến chi tiết có hay không
            .map { it.first }
    }

}