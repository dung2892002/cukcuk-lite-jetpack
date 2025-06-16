package com.example.domain.usecase.invoice

import com.example.domain.model.Invoice
import com.example.domain.repository.InvoiceRepository
import java.util.UUID

class GetInvoiceDetailUseCase (
    private val repository: InvoiceRepository
) {
    suspend operator fun invoke(invoiceId: UUID) : Invoice {
        return repository.getInvoiceById(invoiceId) ?: Invoice()
    }
}