package com.example.cukcuk.presentation.ui.inventory.inventory_form

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cukcuk.domain.model.Inventory
import com.example.cukcuk.domain.model.Unit
import com.example.cukcuk.domain.usecase.inventory.CreateInventoryUseCase
import com.example.cukcuk.domain.usecase.inventory.DeleteInventoryUseCase
import com.example.cukcuk.domain.usecase.inventory.GetInventoryDetailUseCase
import com.example.cukcuk.domain.usecase.inventory.UpdateInventoryUseCase
import com.example.cukcuk.presentation.enums.ColorInventory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class InventoryFormViewModel @Inject constructor(
    private val createInventoryUseCase: CreateInventoryUseCase,
    private val updateInventoryUseCase: UpdateInventoryUseCase,
    private val deleteInventoryUseCase: DeleteInventoryUseCase,
    private val getInventory: GetInventoryDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _inventory = mutableStateOf(Inventory())
    val inventory: State<Inventory> = _inventory

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _showSelectColor = mutableStateOf(false)
    val showSelectColor: State<Boolean> = _showSelectColor

    private val _showSelectImage = mutableStateOf(false)
    val showSelectImage: State<Boolean> = _showSelectImage

    private val _showCalculator = mutableStateOf(false)
    val showCalculator: State<Boolean> = _showCalculator

    private val _isOpenDialogDelete = mutableStateOf(false)
    val isOpenDialogDelete: State<Boolean> = _isOpenDialogDelete

    private val _isSubmitSuccess = mutableStateOf(false)
    val isSubmitSuccess: State<Boolean> = _isSubmitSuccess


    init {
        viewModelScope.launch {
            val id = savedStateHandle.get<String>("inventoryId")?.let { UUID.fromString(it) }
            if (id != null)
                loadInventory(id)
        }
    }

    suspend fun loadInventory(inventoryID: UUID) {
        val data = getInventory(inventoryID)
        _inventory.value = data
    }

    fun submit() {
        viewModelScope.launch {
            val response = if (_inventory.value.InventoryID == null) {
                createInventoryUseCase(_inventory.value.copy())
            } else {
                updateInventoryUseCase(_inventory.value.copy())
            }

            updateErrorMessage(response.message)
            updateSuccessState(response.isSuccess)
        }
    }

    fun delete() {
        viewModelScope.launch {
            val response = deleteInventoryUseCase(_inventory.value)
            if (response.isSuccess) closeDialogDelete()

            updateErrorMessage(response.message)
            updateSuccessState(response.isSuccess)
        }
    }

    fun updateSuccessState(state: Boolean) {
        _isSubmitSuccess.value = state
    }

    fun updateErrorMessage(msg: String?) {
        _errorMessage.value = msg
    }

    fun openSelectColor() {
        _showSelectColor.value = true
    }

    fun openSelectImage() {
        _showSelectImage.value = true
    }

    fun closeSelectColor() {
        _showSelectColor.value = false
    }

    fun closeSelectImage() {
        _showSelectImage.value = false
    }

    fun openDialogDelete() {
        _isOpenDialogDelete.value = true
    }

    fun closeDialogDelete() {
        _isOpenDialogDelete.value = false
    }

    fun openCalculator() {
        _showCalculator.value = true
    }

    fun closeCalculator() {
        _showCalculator.value = false
    }

    fun updateInventoryName(name: String) {
        _inventory.value = _inventory.value.copy(InventoryName = name)
    }

    fun updatePrice(price: Double) {
        _inventory.value = _inventory.value.copy(Price = price)
        closeCalculator()
    }

    fun updateColor(color: ColorInventory) {
        _inventory.value = _inventory.value.copy(Color = color.value)
        closeSelectColor()
    }

    fun updateIconFileName(image: String) {
        _inventory.value = _inventory.value.copy(IconFileName = image)
        closeSelectImage()
    }

    fun updateUnit(unit: Unit) {
        _inventory.value = _inventory.value.copy(UnitID = unit.UnitID, UnitName = unit.UnitName)
    }

    fun updateInactive(state: Boolean) {
        _inventory.value = _inventory.value.copy(Inactive = !state)
    }

    fun buildDialogContent() : AnnotatedString {
        return buildAnnotatedString {
            append("Bạn có chắc muốn xóa món ăn ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append(inventory.value.InventoryName)
            }
            append(" không?")
        }
    }
}