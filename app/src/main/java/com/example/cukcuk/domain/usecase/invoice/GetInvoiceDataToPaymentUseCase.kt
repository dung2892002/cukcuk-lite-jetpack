package com.example.cukcuk.domain.usecase.invoice

import com.example.cukcuk.domain.model.Invoice
import com.example.cukcuk.domain.repository.InvoiceRepository
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class GetInvoiceDataToPaymentUseCase @Inject constructor(
    private val repository: InvoiceRepository
) {
    suspend operator fun invoke(invoiceId: UUID) : Invoice {
        val invoice = repository.getInvoiceById(invoiceId) ?: Invoice()
        invoice.InvoiceNo = repository.getNewInvoiceNo()
        invoice.InvoiceDate = LocalDateTime.now()
        invoice.InvoiceDetails = repository.getListInvoicesDetail(invoiceId)

        return invoice
    }
}