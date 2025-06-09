package com.example.cukcuk.data.repository

import com.example.cukcuk.data.local.dao.InvoiceDao
import com.example.cukcuk.data.local.mapper.toDomain
import com.example.cukcuk.data.local.mapper.toDomainInventory
import com.example.cukcuk.data.local.mapper.toEntity
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