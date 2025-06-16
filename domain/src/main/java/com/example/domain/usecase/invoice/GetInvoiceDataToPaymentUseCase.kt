package com.example.domain.usecase.invoice

import com.example.domain.model.Invoice
import com.example.domain.repository.InvoiceRepository
import java.time.LocalDateTime
import java.util.UUID

class GetInvoiceDataToPaymentUseCase (
    private val repository: InvoiceRepository
) {
    suspend operator fun invoke(invoiceId: UUID) : Invoice {
        val invoice = repository.getInvoiceById(invoiceId) ?: Invoice()
        invoice.InvoiceDate = LocalDateTime.now()



        invoice.InvoiceNo = repository.getNewInvoiceNo()
        invoice.InvoiceDetails = repository.getListInvoicesDetail(invoiceId)

        return invoice
    }
}