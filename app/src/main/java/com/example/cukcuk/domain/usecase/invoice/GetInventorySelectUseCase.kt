package com.example.cukcuk.domain.usecase.invoice

import androidx.compose.runtime.mutableDoubleStateOf
import com.example.cukcuk.domain.dtos.InventorySelect
import com.example.cukcuk.domain.repository.InvoiceRepository
import java.util.UUID
import javax.inject.Inject

class GetInventorySelectUseCase @Inject constructor(
    private val repository: InvoiceRepository
) {
    operator fun invoke(invoiceId: UUID?): List<InventorySelect> {
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

                Triple(InventorySelect(inventory, mutableDoubleStateOf(quantity)), sortOrder, hasDetail)
            }
            .sortedWith(compareBy({ it.second }, { !it.third })) // sortOrder, rồi đến chi tiết có hay không
            .map { it.first }
    }

}