package com.example.presentation.ui.invoice.invoice_form

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.InventorySelect
import com.example.domain.model.Invoice
import com.example.domain.usecase.invoice.CreateInvoiceUseCase
import com.example.domain.usecase.invoice.GetInventorySelectUseCase
import com.example.domain.usecase.invoice.GetInvoiceDetailUseCase
import com.example.domain.usecase.invoice.UpdateInvoiceUseCase
import com.example.presentation.mapper.getErrorMessage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID

class InvoiceFormViewModel(
    private val getInvoiceDetailUseCase: GetInvoiceDetailUseCase,
    private val getInventorySelectUseCase: GetInventorySelectUseCase,
    private val updateInvoiceUseCase: UpdateInvoiceUseCase,
    private val createInvoiceUseCase: CreateInvoiceUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _invoice = mutableStateOf<Invoice>(Invoice())
    val invoice: State<Invoice> = _invoice

    private val _inventoriesSelect = mutableStateOf<List<InventorySelect>>(emptyList())
    val inventoriesSelect: State<List<InventorySelect>> = _inventoriesSelect

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

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    init {
        val id = savedStateHandle.get<String>("invoiceId")?.let { UUID.fromString(it) }
        fetchData(id)
    }

    fun fetchData(invoiceId: UUID?) {
        viewModelScope.launch {
            try {
                _loading.value = true
                delay(200)
                if (invoiceId != null) _invoice.value = getInvoiceDetailUseCase(invoiceId)
                _inventoriesSelect.value = getInventorySelectUseCase(invoiceId)
            }
            catch (e: Exception) {
                _errorMessage.value = e.message
            }
            finally {
                _loading.value = false
            }
        }
    }

    suspend fun submitForm(context: Context) : UUID? {
        try {
            val response = if (invoice.value.InvoiceID == null) {
                createInvoiceUseCase(_invoice.value, _inventoriesSelect.value)
            } else {
                updateInvoiceUseCase(_invoice.value, _inventoriesSelect.value)
            }

            if (response.isSuccess) {
                val invoiceData = response.objectData as Invoice
                val id = invoiceData.InvoiceID
                _errorMessage.value = null
                return id
            }

            _errorMessage.value = response.getErrorMessage(context)
            return null
        }
        catch (e: Exception) {
            _errorMessage.value = e.message
            return null
        }
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

    fun updateQuantityInventory(newQuantity: Double) {
        val index = _currentInventoryIndex.intValue
        updateQuantityInventoryWithIndex(index,newQuantity)

        closeCalculator()
    }

    fun updateQuantityInventoryWithIndex(index: Int, newQuantity: Double) {
        val item = _inventoriesSelect.value[index]
        val oldQuantity = item.quantity

        val updatedItem = item.copy(quantity = newQuantity)
        val updatedList = _inventoriesSelect.value.toMutableList().apply {
            set(index, updatedItem)
        }
        _inventoriesSelect.value = updatedList

        val quantityDiff = newQuantity - oldQuantity
        _invoice.value = _invoice.value.copy(
            Amount = _invoice.value.Amount + quantityDiff * item.inventory.Price
        )
    }

    fun updateNewTable(newTable: String) {
        _invoice.value = _invoice.value.copy(TableName = newTable)
        closeCalculator()
    }

    fun updateNewNumberPeople(value: String) {
        if (value.isNotEmpty()) _invoice.value = _invoice.value.copy(NumberOfPeople = value.toDouble().toInt())
        closeCalculator()
    }

    fun closeCalculator() {
        _showCalculatorQuantity.value = false
        _showCalculatorTableName.value = false
        _showCalculatorNumberPeople.value = false
    }

    fun setErrorMessage(message: String?) {
        _errorMessage.value = message
    }
}