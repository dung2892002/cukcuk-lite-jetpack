package com.example.cukcuk.domain.usecase.invoice

import com.example.cukcuk.domain.model.Invoice
import com.example.cukcuk.domain.repository.InvoiceRepository
import javax.inject.Inject

class GetInvoicesNotPaymentUseCase @Inject constructor(
    private val repository: InvoiceRepository
) {
    operator fun invoke() : List<Invoice> {
        return repository.getListInvoiceNotPayment()
    }
}