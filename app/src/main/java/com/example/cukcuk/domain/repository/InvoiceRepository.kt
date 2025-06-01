package com.example.cukcuk.domain.repository

import com.example.cukcuk.domain.model.Inventory
import com.example.cukcuk.domain.model.Invoice
import com.example.cukcuk.domain.model.InvoiceDetail
import java.util.UUID

interface InvoiceRepository {
    fun getNewInvoiceNo() : String
    fun getListInvoiceNotPayment() : MutableList<Invoice>
    fun getListInvoicesDetail(invoiceId: UUID) : MutableList<InvoiceDetail>
    fun deleteInvoice(invoiceId: String) : Boolean
    fun getInvoiceById(invoiceId: UUID): Invoice?
    fun getInvoiceDetailById(invoiceDetailId: UUID): InvoiceDetail?
    fun getAllInventoryInactive() : MutableList<Inventory>
    fun createInvoice(invoice: Invoice): Boolean
    fun updateInvoice(invoice: Invoice,
                      newsDetail: MutableList<InvoiceDetail>,
                      updatesDetail: MutableList<InvoiceDetail>,
                      deletesDetail: MutableList<InvoiceDetail>): Boolean
    fun paymentInvoice(invoice: Invoice): Boolean
}