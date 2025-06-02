package com.example.cukcuk.presentation.ui.invoice.invoice_payment

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cukcuk.domain.dtos.ResponseData
import com.example.cukcuk.domain.model.Invoice
import com.example.cukcuk.domain.usecase.invoice.GetInvoiceDataToPaymentUseCase
import com.example.cukcuk.domain.usecase.invoice.PaymentInvoiceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val getInvoiceDataToPaymentUseCase: GetInvoiceDataToPaymentUseCase,
    private val paymentInvoiceUseCase: PaymentInvoiceUseCase
) : ViewModel() {

    private val _invoice = mutableStateOf(Invoice(
        InvoiceDate = LocalDateTime.now(),
    ))
    val invoice: State<Invoice> = _invoice

    private val _showCalculator = mutableStateOf(false)
    val showCalculator: State<Boolean> = _showCalculator


    fun loadData(invoiceId: UUID) {
        _invoice.value = getInvoiceDataToPaymentUseCase(invoiceId)
    }

    fun updateAmount(receiveMoney: Double) {
        _invoice.value = _invoice.value.copy(
            ReceiveAmount = receiveMoney,
            ReturnAmount = receiveMoney - _invoice.value.Amount)

        closeCalculator()
    }

    fun paymentInvoice(): ResponseData {
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