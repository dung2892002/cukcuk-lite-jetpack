package com.example.cukcuk.domain.usecase.invoice

import com.example.cukcuk.domain.common.ResponseData
import com.example.cukcuk.domain.model.Invoice
import com.example.cukcuk.domain.repository.InvoiceRepository
import com.example.cukcuk.presentation.enums.SynchronizeTable
import com.example.cukcuk.utils.SynchronizeHelper
import javax.inject.Inject

class DeleteInvoiceUseCase @Inject constructor(
    private val repository: InvoiceRepository,
    private val syncHelper: SynchronizeHelper
) {
    suspend operator fun invoke(invoice: Invoice) : ResponseData {
        val response = ResponseData(false, "Có lỗi xảy ra")

        val invoicesDetail = repository.getListInvoicesDetail(invoice.InvoiceID!!)
        response.isSuccess =  repository.deleteInvoice(invoice.InvoiceID.toString())
        if (response.isSuccess) {
            response.message = null
            syncHelper.deleteInvoiceDetail(invoicesDetail)
            syncHelper.deleteSync(SynchronizeTable.Invoice, invoice.InvoiceID)
        }

        return response
    }
}