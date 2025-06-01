package com.example.cukcuk.domain.usecase.invoice

import com.example.cukcuk.domain.repository.InvoiceRepository
import javax.inject.Inject

class GetNewInvoiceNoUseCase @Inject constructor(
    private val repository: InvoiceRepository
) {
    operator fun invoke() : String {
        return repository.getNewInvoiceNo()
    }
}