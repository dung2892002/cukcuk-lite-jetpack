package com.example.cukcuk.domain.usecase.invoice

import com.example.cukcuk.domain.common.ResponseData
import com.example.cukcuk.domain.model.Invoice
import com.example.cukcuk.domain.repository.InvoiceRepository
import com.example.cukcuk.presentation.enums.SynchronizeTable
import com.example.cukcuk.utils.SynchronizeHelper
import java.time.LocalDateTime
import javax.inject.Inject

class PaymentInvoiceUseCase @Inject constructor(
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