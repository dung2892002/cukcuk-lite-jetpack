package com.example.domain.usecase.invoice

import com.example.domain.model.ResponseData
import com.example.domain.model.Invoice
import com.example.domain.repository.InvoiceRepository
import com.example.domain.enums.SynchronizeTable
import com.example.domain.utils.SynchronizeHelper
import java.time.LocalDateTime

class PaymentInvoiceUseCase(
    private val repository: InvoiceRepository,
    private val syncHelper: SynchronizeHelper
){
    suspend operator fun invoke(invoice: Invoice) : ResponseData {
        var response = ResponseData(false, "Có lỗi xảy ra")

        invoice.InvoiceDate = LocalDateTime.now()

        response.isSuccess = repository.paymentInvoice(invoice)

        if (response.isSuccess) {
            response.message = null
            syncHelper.updateSync(SynchronizeTable.Invoice, invoice.InvoiceID)
        }

        return response
    }
}