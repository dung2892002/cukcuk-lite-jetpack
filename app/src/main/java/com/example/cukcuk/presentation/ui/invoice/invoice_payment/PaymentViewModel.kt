package com.example.cukcuk.presentation.ui.invoice.invoice_payment

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cukcuk.domain.model.ResponseData
import com.example.cukcuk.domain.model.Invoice
import com.example.cukcuk.domain.usecase.invoice.GetInvoiceDataToPaymentUseCase
import com.example.cukcuk.domain.usecase.invoice.PaymentInvoiceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val getInvoiceDataToPaymentUseCase: GetInvoiceDataToPaymentUseCase,
    private val paymentInvoiceUseCase: PaymentInvoiceUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _invoice = mutableStateOf(Invoice(
        InvoiceDate = LocalDateTime.now(),
    ))
    val invoice: State<Invoice> = _invoice

    private val _invoiceDetailsCount = mutableIntStateOf(0)
    val invoiceDetailCounts: State<Int> = _invoiceDetailsCount

    private val _showCalculator = mutableStateOf(false)
    val showCalculator: State<Boolean> = _showCalculator

    init {
        val currentUnitId = savedStateHandle.get<String>("invoiceId")?.let { UUID.fromString(it) }
        if (currentUnitId != null) {
            loadData(currentUnitId)
        }
    }

    fun loadData(invoiceId: UUID) {
        viewModelScope.launch {
            _invoice.value = getInvoiceDataToPaymentUseCase(invoiceId)
            _invoiceDetailsCount.intValue = _invoice.value.InvoiceDetails.size
        }
    }

    fun updateAmount(receiveMoney: Double) {
        _invoice.value = _invoice.value.copy(
            ReceiveAmount = receiveMoney,
            ReturnAmount = receiveMoney - _invoice.value.Amount)

        closeCalculator()
    }

    suspend fun paymentInvoice(): ResponseData {
        val response = paymentInvoiceUseCase(_invoice.value)
        return response
    }

    fun openCalculator() {
        _showCalculator.value = true
    }

    fun closeCalculator() {
        _showCalculator.value = false
    }

}