package com.example.cukcuk.domain.usecase.invoice

import com.example.cukcuk.domain.dtos.ResponseData
import com.example.cukcuk.domain.model.Invoice
import com.example.cukcuk.domain.repository.InvoiceRepository
import java.time.LocalDateTime
import javax.inject.Inject

class PaymentInvoiceUseCase @Inject constructor(
    private val repository: InvoiceRepository
){
    operator fun invoke(invoice: Invoice) : ResponseData {
        var response = ResponseData(false, "Có lỗi xảy ra")

        response.isSuccess = repository.paymentInvoice(invoice)
        if (response.isSuccess) response.message = null

        return response
    }
}