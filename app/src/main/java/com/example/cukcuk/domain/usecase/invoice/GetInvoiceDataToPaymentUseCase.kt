package com.example.cukcuk.domain.usecase.invoice

import com.example.cukcuk.domain.model.Invoice
import com.example.cukcuk.domain.repository.InvoiceRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class GetInvoiceDataToPaymentUseCase @Inject constructor(
    private val repository: InvoiceRepository
) {
    suspend operator fun invoke(invoiceId: UUID) : Invoice {
        val invoice = repository.getInvoiceById(invoiceId) ?: Invoice()
        invoice.InvoiceDate = LocalDateTime.now()

        coroutineScope{
            val defInvoiceNo = async { repository.getNewInvoiceNo() }
            val defInvoiceDetails = async { repository.getListInvoicesDetail(invoiceId) }

            invoice.InvoiceNo = defInvoiceNo.await()
            invoice.InvoiceDetails = defInvoiceDetails.await()
        }

        return invoice
    }
}