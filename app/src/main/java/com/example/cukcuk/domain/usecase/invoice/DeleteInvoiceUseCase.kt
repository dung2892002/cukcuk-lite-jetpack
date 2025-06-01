package com.example.cukcuk.domain.usecase.invoice

import com.example.cukcuk.domain.dtos.ResponseData
import com.example.cukcuk.domain.model.Invoice
import com.example.cukcuk.domain.repository.InvoiceRepository
import javax.inject.Inject

class DeleteInvoiceUseCase @Inject constructor(
    private val repository: InvoiceRepository
) {
    operator fun invoke(invoice: Invoice) : ResponseData {
        val response = ResponseData(false, "Có lỗi xảy ra")

        response.isSuccess =  repository.deleteInvoice(invoice.InvoiceID.toString())
        if (response.isSuccess) response.message =  null

        return response
    }
}