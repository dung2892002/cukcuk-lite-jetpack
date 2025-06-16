package com.example.domain.usecase.invoice

import com.example.domain.model.Invoice
import com.example.domain.repository.InvoiceRepository

class GetInvoicesNotPaymentUseCase (
    private val repository: InvoiceRepository
) {
    suspend operator fun invoke() : List<Invoice> {
        return repository.getListInvoiceNotPayment()
    }
}