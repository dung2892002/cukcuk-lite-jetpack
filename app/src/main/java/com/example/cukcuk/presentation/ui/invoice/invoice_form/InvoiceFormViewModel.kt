package com.example.cukcuk.presentation.ui.invoice.invoice_form

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.cukcuk.domain.dtos.InventorySelect
import com.example.cukcuk.domain.model.Invoice
import com.example.cukcuk.domain.usecase.invoice.CreateInvoiceUseCase
import com.example.cukcuk.domain.usecase.invoice.GetInventorySelectUseCase
import com.example.cukcuk.domain.usecase.invoice.GetInvoiceDetailUseCase
import com.example.cukcuk.domain.usecase.invoice.UpdateInvoiceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class InvoiceFormViewModel @Inject constructor(
    private val getInvoiceDetailUseCase: GetInvoiceDetailUseCase,
    private val getInventorySelectUseCase: GetInventorySelectUseCase,
    private val updateInvoiceUseCase: UpdateInvoiceUseCase,
    private val createInvoiceUseCase: CreateInvoiceUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(){
    private val _invoice = mutableStateOf<Invoice>(Invoice())
    val invoice: State<Invoice> = _invoice

    private val _inventoriesSelect = mutableStateListOf<InventorySelect>()
    val inventoriesSelect: List<InventorySelect> = _inventoriesSelect

    private val _currentInventoryIndex = mutableIntStateOf(0)
    val currentInventoryIndex: State<Int> = _currentInventoryIndex

    private val _showCalculatorQuantity = mutableStateOf(false)
    val showCalculatorQuantity: State<Boolean> = _showCalculatorQuantity

    private val _showCalculatorTableName = mutableStateOf(false)
    val showCalculatorTableName: State<Boolean> = _showCalculatorTableName

    private val _showCalculatorNumberPeople = mutableStateOf(false)
    val showCalculatorNumberPeople: State<Boolean> = _showCalculatorNumberPeople

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage


    init {
        val id = savedStateHandle.get<String>("invoiceId")?.let { UUID.fromString(it) }
        fetchData(id)
    }

    fun fetchData(invoiceId: UUID?) {
        if (invoiceId != null) _invoice.value = getInvoiceDetailUseCase(invoiceId)
        _inventoriesSelect.addAll(getInventorySelectUseCase(invoiceId))
    }

    fun submitForm() : UUID? {
        val response = if (invoice.value.InvoiceID == null) {
            createInvoiceUseCase(_invoice.value, _inventoriesSelect)
        } else {
            updateInvoiceUseCase(_invoice.value, _inventoriesSelect)
        }

        if (response.isSuccess) {
            val id = response.message
            _errorMessage.value = null
            return UUID.fromString(id)
        }

        _errorMessage.value = response.message
        return null
    }

    fun openCalculatorQuantity(index: Int) {
        _currentInventoryIndex.intValue= index
        _showCalculatorQuantity.value = true
    }

    fun openCalculatorTableName() {
        _showCalculatorTableName.value = true
    }

    fun openCalculatorNumberPeople() {
        _showCalculatorNumberPeople.value = true
    }

    fun updateNewQuantity(newQuantity: Double) {
        val index = _currentInventoryIndex.intValue
        _invoice.value.Amount += (newQuantity - _inventoriesSelect[index].quantity.value) * _inventoriesSelect[index].inventory.Price
        _inventoriesSelect[index].quantity.value = newQuantity
        closeCalculator()
    }

    fun updateNewTable(newTable: String) {
        _invoice.value = _invoice.value.copy(TableName = newTable)
        closeCalculator()
    }

    fun updateNewNumberPeople(newNumberPeople: Int) {
        _invoice.value = _invoice.value.copy(NumberOfPeople = newNumberPeople)
        closeCalculator()
    }

    fun updateAmount(amount: Double) {
        val newAmount = invoice.value.Amount + amount
        _invoice.value = _invoice.value.copy(Amount = newAmount)
    }

    fun closeCalculator() {
        _showCalculatorQuantity.value = false
        _showCalculatorTableName.value = false
        _showCalculatorNumberPeople.value = false
    }
}