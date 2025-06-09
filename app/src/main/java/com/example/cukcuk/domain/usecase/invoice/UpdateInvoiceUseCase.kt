package com.example.cukcuk.domain.usecase.invoice

import com.example.cukcuk.domain.dtos.InventorySelect
import com.example.cukcuk.domain.dtos.InvoiceDetailChangeResult
import com.example.cukcuk.domain.dtos.ResponseData
import com.example.cukcuk.domain.model.Invoice
import com.example.cukcuk.domain.model.InvoiceDetail
import com.example.cukcuk.domain.repository.InvoiceRepository
import com.example.cukcuk.presentation.enums.SynchronizeTable
import com.example.cukcuk.utils.FormatDisplay
import com.example.cukcuk.utils.SynchronizeHelper
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class UpdateInvoiceUseCase @Inject constructor(
    private val repository: InvoiceRepository,
    private val syncHelper: SynchronizeHelper
) {
    suspend operator fun invoke(invoice: Invoice,
                        inventoriesSelect: List<InventorySelect>) : ResponseData {
        var response = ResponseData(false, "Có lỗi xảy ra")

        if (invoice.Amount == 0.0) {
            response.message = "Vui lòng chọn món"
            return response
        }

        val lastInvoicesDetail = if (invoice.InvoiceID != null) repository.getListInvoicesDetail(invoice.InvoiceID!!) else mutableListOf<InvoiceDetail>()
        val filteredList = inventoriesSelect.filter { it.quantity.value > 0.0 }.toMutableList<InventorySelect>()

        val result = handleProcessInvoiceDetails(invoice, lastInvoicesDetail, filteredList)

        invoice.InvoiceDetails = (result.toCreate + result.toUpdate + result.unchanged).sortedBy { it.SortOrder }.toMutableList()
        invoice.ListItemName = buildListItemName(invoice.InvoiceDetails)
        invoice.ReceiveAmount = invoice.Amount

        response.isSuccess = repository.updateInvoice(invoice, result.toCreate, result.toUpdate, result.toDelete)
        if (response.isSuccess) {
            response.message = invoice.InvoiceID.toString()
            syncHelper.updateSync(SynchronizeTable.Invoice, invoice.InvoiceID)

            coroutineScope {
                awaitAll(
                    async { syncHelper.deleteInvoiceDetail(result.toDelete) },
                    async { syncHelper.createInvoiceDetail(result.toCreate) },
                    async { syncHelper.updateInvoiceDetail(result.toUpdate) }
                )
            }

        }

        return response
    }

    private fun buildListItemName(invoiceDetails: List<InvoiceDetail>) : String {
        var builder = StringBuilder()
        invoiceDetails.forEachIndexed { index, item ->
            val itemName = item.InventoryName
            val quantity = FormatDisplay.formatNumber(item.Quantity.toString())

            builder.append("$itemName ($quantity)")
            if (index != invoiceDetails.lastIndex) {
                builder.append(", ")
            }
        }

        return builder.toString()
    }

    private fun handleProcessInvoiceDetails(
        invoice: Invoice,
        details: MutableList<InvoiceDetail>,
        inventorySelects: MutableList<InventorySelect>
    ): InvoiceDetailChangeResult {

        val toCreate = mutableListOf<InvoiceDetail>()
        val toUpdate = mutableListOf<InvoiceDetail>()
        val toDelete = mutableListOf<InvoiceDetail>()
        val unchanged = mutableListOf<InvoiceDetail>()

        val currentDetailMap = details.associateBy { it.InventoryID }
        val selectedInventoryIds = inventorySelects.map { it.inventory.InventoryID }.toSet()

        var sortOrder = 0

        for (it in inventorySelects) {
            val matchingDetail = currentDetailMap[it.inventory.InventoryID]
            sortOrder++
            if (matchingDetail == null) {
                val newDetail = InvoiceDetail(
                    InvoiceDetailID = UUID.randomUUID(),
                    InvoiceID = invoice.InvoiceID,
                    InventoryID = it.inventory.InventoryID,
                    InventoryName = it.inventory.InventoryName,
                    UnitID = it.inventory.UnitID,
                    UnitName = it.inventory.UnitName,
                    Quantity = it.quantity.value,
                    UnitPrice = it.inventory.Price,
                    Amount = it.quantity.value * it.inventory.Price,
                    SortOrder = sortOrder,
                    CreatedDate = LocalDateTime.now()
                )
                toCreate.add(newDetail)
            } else {
                if (matchingDetail.Quantity != it.quantity.value ) {
                    val updatedDetail = matchingDetail.copy(
                        Quantity = it.quantity.value,
                        UnitPrice = it.inventory.Price,
                        Amount = it.quantity.value * it.inventory.Price,
                        SortOrder = sortOrder,
                        ModifiedDate = LocalDateTime.now()
                    )
                    toUpdate.add(updatedDetail)
                }

                else {
                    val details = matchingDetail.copy(
                        Quantity = it.quantity.value,
                        UnitPrice = it.inventory.Price,
                        SortOrder = sortOrder
                    )
                    unchanged.add(details)
                }
            }
        }

        for (detail in details) {
            if (detail.InventoryID !in selectedInventoryIds) {
                toDelete.add(detail)
            }
        }

        return InvoiceDetailChangeResult(
            toCreate = toCreate,
            toUpdate = toUpdate,
            toDelete = toDelete,
            unchanged = unchanged
        )
    }
}