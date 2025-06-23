package com.example.domain.usecase.invoice

import com.example.domain.enums.DomainError
import com.example.domain.model.InventorySelect
import com.example.domain.model.InvoiceDetailChangeResult
import com.example.domain.model.ResponseData
import com.example.domain.model.Invoice
import com.example.domain.model.InvoiceDetail
import com.example.domain.repository.InvoiceRepository
import com.example.domain.enums.SynchronizeTable
import com.example.domain.utils.FormatDisplay
import com.example.domain.utils.SynchronizeHelper
import java.time.LocalDateTime
import java.util.UUID

class UpdateInvoiceUseCase (
    private val repository: InvoiceRepository,
    private val syncHelper: SynchronizeHelper
) {
    suspend operator fun invoke(invoice: Invoice,
                        inventoriesSelect: List<InventorySelect>) : ResponseData<Invoice>  {
        var response = ResponseData<Invoice> (false, DomainError.UNKNOWN_ERROR)

        if (invoice.Amount == 0.0) {
            response.error = DomainError.INVOICE_BLANK
            return response
        }

        val lastInvoicesDetail = if (invoice.InvoiceID != null) repository.getListInvoicesDetail(invoice.InvoiceID!!) else mutableListOf<InvoiceDetail>()
        val filteredList = inventoriesSelect.filter { it.quantity > 0.0 }.toMutableList<InventorySelect>()

        val result = handleProcessInvoiceDetails(invoice, lastInvoicesDetail, filteredList)

        invoice.InvoiceDetails = (result.toCreate + result.toUpdate + result.unchanged).sortedBy { it.SortOrder }.toMutableList()
        invoice.ListItemName = buildListItemName(invoice.InvoiceDetails)
        invoice.ReceiveAmount = invoice.Amount

        response.isSuccess = repository.updateInvoice(invoice, result.toCreate, result.toUpdate, result.toDelete)
        if (response.isSuccess) {
            response.objectData = invoice
            syncHelper.updateSync(SynchronizeTable.Invoice, invoice.InvoiceID)
            syncHelper.deleteInvoiceDetail(result.toDelete)
            syncHelper.createInvoiceDetail(result.toCreate)
            syncHelper.updateInvoiceDetail(result.toUpdate)
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
                    Quantity = it.quantity,
                    UnitPrice = it.inventory.Price,
                    Amount = it.quantity * it.inventory.Price,
                    SortOrder = sortOrder,
                    CreatedDate = LocalDateTime.now()
                )
                toCreate.add(newDetail)
            } else {
                if (matchingDetail.Quantity != it.quantity ) {
                    val updatedDetail = matchingDetail.copy(
                        Quantity = it.quantity,
                        UnitPrice = it.inventory.Price,
                        Amount = it.quantity * it.inventory.Price,
                        SortOrder = sortOrder,
                        ModifiedDate = LocalDateTime.now()
                    )
                    toUpdate.add(updatedDetail)
                }

                else {
                    val details = matchingDetail.copy(
                        Quantity = it.quantity,
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