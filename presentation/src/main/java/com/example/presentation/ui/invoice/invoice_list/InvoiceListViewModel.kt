package com.example.presentation.ui.invoice.invoice_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Invoice
import com.example.domain.usecase.invoice.DeleteInvoiceUseCase
import com.example.domain.usecase.invoice.GetInvoicesNotPaymentUseCase
import kotlinx.coroutines.launch


class InvoiceListViewModel (
    private val getInvoicesNotPaymentUseCase: GetInvoicesNotPaymentUseCase,
    private val deleteInvoiceUseCase: DeleteInvoiceUseCase
) : ViewModel() {

    private val _invoices = mutableStateOf<List<Invoice>>(emptyList())
    val invoices: State<List<Invoice>> = _invoices

    private val _showDeleteDialog = mutableStateOf(false)
    val showDeleteDialog: State<Boolean> = _showDeleteDialog

    private val _selectedInvoice = mutableStateOf<Invoice?>(null)
    val selectedInvoice: State<Invoice?> = _selectedInvoice

    val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    init {
        loadInvoiceNotPayment()
    }

    fun loadInvoiceNotPayment() {
        viewModelScope.launch {
            val invoicesData = getInvoicesNotPaymentUseCase()
            _invoices.value = invoicesData
        }
    }

    fun openDialogDelete(invoice: Invoice) {
        _selectedInvoice.value = invoice
        _showDeleteDialog.value = true
    }

    fun closeDialogDelete() {
        _showDeleteDialog.value = false
        _selectedInvoice.value = null
    }

    fun deleteInvoice() {
        viewModelScope.launch {
            val response = deleteInvoiceUseCase(selectedInvoice.value!!)
            _errorMessage.value = response.message
            if (response.isSuccess) {
                loadInvoiceNotPayment()
                closeDialogDelete()
            }
        }
    }
}