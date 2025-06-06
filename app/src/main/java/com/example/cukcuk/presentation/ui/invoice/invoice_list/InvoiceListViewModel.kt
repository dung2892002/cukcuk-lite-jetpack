package com.example.cukcuk.presentation.ui.invoice.invoice_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cukcuk.domain.model.Invoice
import com.example.cukcuk.domain.usecase.invoice.DeleteInvoiceUseCase
import com.example.cukcuk.domain.usecase.invoice.GetInvoicesNotPaymentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class InvoiceListViewModel @Inject constructor(
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