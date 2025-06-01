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
    override fun getNewInvoiceNo(): String {
        return dao.getNewInvoiceNo()
    }

    override fun getListInvoiceNotPayment(): MutableList<Invoice> {
        return dao.getListInvoiceNotPayment()
    }

    override fun getListInvoicesDetail(invoiceId: UUID): MutableList<InvoiceDetail> {
        return dao.getListInvoicesDetail(invoiceId)
    }

    override fun deleteInvoice(invoiceId: String): Boolean {
        return dao.deleteInvoice(invoiceId)
    }

    override fun getInvoiceById(invoiceId: UUID): Invoice? {
        return dao.getInvoiceById(invoiceId)
    }

    override fun getInvoiceDetailById(invoiceDetailId: UUID): InvoiceDetail? {
        return dao.getInvoiceDetailById(invoiceDetailId)
    }

    override fun getAllInventoryInactive(): MutableList<Inventory> {
        return dao.getAllInventoryInactive()
    }

    override fun createInvoice(invoice: Invoice): Boolean {
        return dao.createInvoice(invoice)
    }

    override fun updateInvoice(
        invoice: Invoice,
        newsDetail: MutableList<InvoiceDetail>,
        updatesDetail: MutableList<InvoiceDetail>,
        deletesDetail: MutableList<InvoiceDetail>
    ): Boolean {
        return dao.updateInvoice(invoice, newsDetail, updatesDetail, deletesDetail)
    }

    override fun paymentInvoice(invoice: Invoice): Boolean {
        return dao.paymentInvoice(invoice)
    }
}