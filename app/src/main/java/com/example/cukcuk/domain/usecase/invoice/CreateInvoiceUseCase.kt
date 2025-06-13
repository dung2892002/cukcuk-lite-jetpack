package com.example.cukcuk.domain.usecase.invoice

import com.example.cukcuk.domain.model.InventorySelect
import com.example.cukcuk.domain.common.ResponseData
import com.example.cukcuk.domain.model.Invoice
import com.example.cukcuk.domain.model.InvoiceDetail
import com.example.cukcuk.domain.repository.InvoiceRepository
import com.example.cukcuk.domain.enums.SynchronizeTable
import com.example.cukcuk.utils.FormatDisplay
import com.example.cukcuk.utils.SynchronizeHelper
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class CreateInvoiceUseCase @Inject constructor(
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
            response.message = invoice.InvoiceID.toString()
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