package com.example.cukcuk.data.repository

import com.example.cukcuk.data.local.dao.InvoiceDao
import com.example.cukcuk.domain.model.Inventory
import com.example.cukcuk.domain.model.Invoice
import com.example.cukcuk.domain.model.InvoiceDetail
import com.example.cukcuk.domain.repository.InvoiceRepository
import java.util.UUID
import javax.inject.Inject

class InvoiceRepositoryImpl @Inject constructor(
    private val dao: InvoiceDao
) : InvoiceRepository {
    override suspend fun getNewInvoiceNo(): String {
        return dao.getNewInvoiceNo()
    }

    override suspend fun getListInvoiceNotPayment(): MutableList<Invoice> {
        return dao.getListInvoiceNotPayment()
    }

    override suspend fun getListInvoicesDetail(invoiceId: UUID): MutableList<InvoiceDetail> {
        return dao.getListInvoicesDetail(invoiceId)
    }

    override suspend fun deleteInvoice(invoiceId: String): Boolean {
        return dao.deleteInvoice(invoiceId)
    }

    override suspend fun getInvoiceById(invoiceId: UUID): Invoice? {
        return dao.getInvoiceById(invoiceId)
    }

    override suspend fun getInvoiceDetailById(invoiceDetailId: UUID): InvoiceDetail? {
        return dao.getInvoiceDetailById(invoiceDetailId)
    }

    override suspend fun getAllInventoryInactive(): MutableList<Inventory> {
        return dao.getAllInventoryInactive()
    }

    override suspend fun createInvoice(invoice: Invoice): Boolean {
        return dao.createInvoice(invoice)
    }

    override suspend fun updateInvoice(
        invoice: Invoice,
        newsDetail: MutableList<InvoiceDetail>,
        updatesDetail: MutableList<InvoiceDetail>,
        deletesDetail: MutableList<InvoiceDetail>
    ): Boolean {
        return dao.updateInvoice(invoice, newsDetail, updatesDetail, deletesDetail)
    }

    override suspend fun paymentInvoice(invoice: Invoice): Boolean {
        return dao.paymentInvoice(invoice)
    }
}