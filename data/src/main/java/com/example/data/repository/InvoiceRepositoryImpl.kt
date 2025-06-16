package com.example.data.repository

import com.example.data.local.dao.InvoiceDao
import com.example.data.local.mapper.toDomain
import com.example.data.local.mapper.toDomainInventory
import com.example.data.local.mapper.toEntity
import com.example.domain.model.Inventory
import com.example.domain.model.Invoice
import com.example.domain.model.InvoiceDetail
import com.example.domain.repository.InvoiceRepository
import java.util.UUID

class InvoiceRepositoryImpl (
    private val dao: InvoiceDao
) : InvoiceRepository {
    override suspend fun getNewInvoiceNo(): String {
        return dao.getNewInvoiceNo()
    }

    override suspend fun getListInvoiceNotPayment(): MutableList<Invoice> {
        val invoices = dao.getListInvoiceNotPayment()
        return invoices.map { it -> it.toDomain(emptyList()) }.toMutableList()
    }

    override suspend fun getListInvoicesDetail(invoiceId: UUID): MutableList<InvoiceDetail> {
        val details = dao.getListInvoicesDetail(invoiceId)
        return details.map { it.toDomain() }.toMutableList()
    }

    override suspend fun deleteInvoice(invoiceId: String): Boolean {
        return dao.deleteInvoice(invoiceId)
    }

    override suspend fun getInvoiceById(invoiceId: UUID): Invoice? {
        val invoice = dao.getInvoiceById(invoiceId)
        return invoice?.toDomain(emptyList())
    }

    override suspend fun getInvoiceDetailById(invoiceDetailId: UUID): InvoiceDetail? {
        val detail = dao.getInvoiceDetailById(invoiceDetailId)
        return detail?.toDomain()
    }

    override suspend fun getAllInventoryInactive(): MutableList<Inventory> {
        val inventories = dao.getAllInventoryInactive()
        return inventories.map { it.toDomainInventory() }.toMutableList()
    }

    override suspend fun createInvoice(invoice: Invoice): Boolean {
        return dao.createInvoice(invoice.toEntity(), invoice.InvoiceDetails.map { it.toEntity() })
    }

    override suspend fun updateInvoice(
        invoice: Invoice,
        newsDetail: MutableList<InvoiceDetail>,
        updatesDetail: MutableList<InvoiceDetail>,
        deletesDetail: MutableList<InvoiceDetail>
    ): Boolean {
        return dao.updateInvoice(
            invoice.toEntity(),
            newsDetail.map { it -> it.toEntity() }.toMutableList(),
            updatesDetail.map { it -> it.toEntity() }.toMutableList(),
            deletesDetail.map { it -> it.toEntity() }.toMutableList())
    }

    override suspend fun paymentInvoice(invoice: Invoice): Boolean {
        return dao.paymentInvoice(invoice.toEntity())
    }
}