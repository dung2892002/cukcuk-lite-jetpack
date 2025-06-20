package com.example.domain.usecase.invoice

import com.example.domain.enums.DomainError
import com.example.domain.model.ResponseData
import com.example.domain.model.Invoice
import com.example.domain.repository.InvoiceRepository
import com.example.domain.enums.SynchronizeTable
import com.example.domain.utils.SynchronizeHelper

class DeleteInvoiceUseCase (
    private val repository: InvoiceRepository,
    private val syncHelper: SynchronizeHelper
) {
    suspend operator fun invoke(invoice: Invoice) : ResponseData<Invoice> {
        val response = ResponseData<Invoice>(false, DomainError.UNKNOWN_ERROR)

        val invoicesDetail = repository.getListInvoicesDetail(invoice.InvoiceID!!)
        response.isSuccess =  repository.deleteInvoice(invoice.InvoiceID.toString())
        if (response.isSuccess) {
            response.error = null
            syncHelper.deleteInvoiceDetail(invoicesDetail)
            syncHelper.deleteSync(SynchronizeTable.Invoice, invoice.InvoiceID)
        }

        return response
    }
}