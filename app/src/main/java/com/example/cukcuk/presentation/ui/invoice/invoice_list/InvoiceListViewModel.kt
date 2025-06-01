package com.example.cukcuk.presentation.ui.invoice.invoice_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cukcuk.domain.model.Invoice
import com.example.cukcuk.domain.usecase.invoice.GetInvoicesNotPaymentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class InvoiceListViewModel @Inject constructor(
    private val getInvoicesNotPaymentUseCase: GetInvoicesNotPaymentUseCase
) : ViewModel() {
    private val _invoices = mutableStateOf<List<Invoice>>(emptyList())
    val invoices: State<List<Invoice>> = _invoices

    init {
        loadInvoiceNotPayment()
    }

    fun loadInvoiceNotPayment() {
        viewModelScope.launch {
            val invoicesData = getInvoicesNotPaymentUseCase()
            _invoices.value = invoicesData
        }
    }
}