package com.example.domain.repository

import com.example.domain.model.Inventory
import com.example.domain.model.Invoice
import com.example.domain.model.InvoiceDetail
import java.util.UUID

interface InvoiceRepository {
    suspend fun getNewInvoiceNo() : String
    suspend fun getListInvoiceNotPayment() : MutableList<Invoice>
    suspend fun getListInvoicesDetail(invoiceId: UUID) : MutableList<InvoiceDetail>
    suspend fun deleteInvoice(invoiceId: String) : Boolean
    suspend fun getInvoiceById(invoiceId: UUID): Invoice?
    suspend fun getInvoiceDetailById(invoiceDetailId: UUID): InvoiceDetail?
    suspend fun getAllInventoryInactive() : MutableList<Inventory>
    suspend fun createInvoice(invoice: Invoice): Boolean
    suspend fun updateInvoice(invoice: Invoice,
                      newsDetail: MutableList<InvoiceDetail>,
                      updatesDetail: MutableList<InvoiceDetail>,
                      deletesDetail: MutableList<InvoiceDetail>): Boolean
    suspend fun paymentInvoice(invoice: Invoice): Boolean
}