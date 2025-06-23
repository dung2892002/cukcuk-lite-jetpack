package com.example.domain.usecase.invoice

import com.example.domain.enums.DomainError
import com.example.domain.model.InventorySelect
import com.example.domain.model.ResponseData
import com.example.domain.model.Invoice
import com.example.domain.model.InvoiceDetail
import com.example.domain.repository.InvoiceRepository
import com.example.domain.enums.SynchronizeTable
import com.example.domain.utils.FormatDisplay
import com.example.domain.utils.SynchronizeHelper
import java.time.LocalDateTime
import java.util.UUID

class CreateInvoiceUseCase(
    private val repository: InvoiceRepository,
    private val syncHelper: SynchronizeHelper
) {
    suspend operator fun invoke(invoice: Invoice,
                        inventoriesSelect: List<InventorySelect>) : ResponseData<Invoice> {
        var response = ResponseData<Invoice>(false, DomainError.UNKNOWN_ERROR)

        if (invoice.Amount == 0.0) {
            response.error = DomainError.INVOICE_BLANK
            return response
        }

        invoice.InvoiceID = UUID.randomUUID()
        invoice.CreatedDate = LocalDateTime.now()
        invoice.ReceiveAmount = invoice.Amount

        var invoiceDetails = mutableListOf<InvoiceDetail>()
        var sortOrder = 0
        for(item in inventoriesSelect) {
            if (item.quantity == 0.0) continue
            sortOrder++
            val inventory = item.inventory
            invoiceDetails.add(InvoiceDetail(
                InvoiceDetailID = UUID.randomUUID(),
                InvoiceID = invoice.InvoiceID,
                InventoryID = inventory.InventoryID,
                InventoryName = inventory.InventoryName,
                UnitID = inventory.UnitID,
                UnitName = inventory.UnitName,
                UnitPrice = inventory.Price,
                Quantity = item.quantity,
                Amount = item.quantity * inventory.Price,
                SortOrder = sortOrder,
                CreatedDate = invoice.CreatedDate
            ))
        }
        invoice.InvoiceDetails = invoiceDetails

        invoice.ListItemName = buildListItemName(invoice.InvoiceDetails)

        response.isSuccess = repository.createInvoice(invoice)
        if (response.isSuccess) {
            response.objectData = invoice
            syncHelper.insertSync(SynchronizeTable.Invoice, invoice.InvoiceID)
            syncHelper.createInvoiceDetail(invoiceDetails)
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
}