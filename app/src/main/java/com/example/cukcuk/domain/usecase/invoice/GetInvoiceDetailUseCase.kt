package com.example.cukcuk.domain.usecase.invoice

import com.example.cukcuk.domain.model.Invoice
import com.example.cukcuk.domain.repository.InvoiceRepository
import java.util.UUID
import javax.inject.Inject

class GetInvoiceDetailUseCase @Inject constructor(
    private val repository: InvoiceRepository
) {
    operator fun invoke(invoiceId: UUID) : Invoice {
        return repository.getInvoiceById(invoiceId) ?: Invoice()
    }
}