package com.example.cukcuk.domain.usecase.invoice

import com.example.cukcuk.domain.dtos.ResponseData
import com.example.cukcuk.domain.model.Invoice
import com.example.cukcuk.domain.repository.InvoiceRepository
import com.example.cukcuk.utils.SynchronizeHelper
import javax.inject.Inject

class DeleteInvoiceUseCase @Inject constructor(
    private val repository: InvoiceRepository,
    private val syncHelper: SynchronizeHelper
) {
    operator fun invoke(invoice: Invoice) : ResponseData {
        val response = ResponseData(false, "Có lỗi xảy ra")

        val invoicesDetail = repository.getListInvoicesDetail(invoice.InvoiceID!!)
        response.isSuccess =  repository.deleteInvoice(invoice.InvoiceID.toString())
        if (response.isSuccess) {
            response.message = null
            syncHelper.deleteSync("Invoice", invoice.InvoiceID)
            syncHelper.deleteInvoiceDetail(invoicesDetail)
        }

        return response
    }
}