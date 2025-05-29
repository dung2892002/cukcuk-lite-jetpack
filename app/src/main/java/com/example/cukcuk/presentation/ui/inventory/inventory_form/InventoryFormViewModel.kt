package com.example.cukcuk.presentation.ui.inventory.inventory_form

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cukcuk.domain.model.Inventory
import com.example.cukcuk.domain.model.Unit
import com.example.cukcuk.domain.usecase.inventory.CreateInventoryUseCase
import com.example.cukcuk.domain.usecase.inventory.DeleteInventoryUseCase
import com.example.cukcuk.domain.usecase.inventory.GetInventoryDetailUseCase
import com.example.cukcuk.domain.usecase.inventory.UpdateInventoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class InventoryFormViewModel @Inject constructor(
    private val createInventoryUseCase: CreateInventoryUseCase,
    private val updateInventoryUseCase: UpdateInventoryUseCase,
    private val deleteInventoryUseCase: DeleteInventoryUseCase,
    private val getInventory: GetInventoryDetailUseCase
) : ViewModel() {

    private val _inventory = mutableStateOf(
        Inventory()
    )
    val inventory: State<Inventory> = _inventory

    private val _errorMessage = mutableStateOf<String?>("")
    val errorMessage: State<String?> = _errorMessage

    val _showSelectColor = mutableStateOf(true)
    val showSelectColor: State<Boolean> = _showSelectColor

    fun loadInventory(inventoryID: UUID) {
        viewModelScope.launch {
            val data = getInventory(inventoryID)
            _inventory.value = data
            println("loadInventory: ${data.InventoryName}")

        }
    }

    fun submit() {
        viewModelScope.launch {
            val response = if (_inventory.value.InventoryID == null) {
                println("them moi")
                createInventoryUseCase(_inventory.value)
            } else {
                println("update")
                updateInventoryUseCase(_inventory.value)
            }
            _errorMessage.value = if (response) null else "co loi xay ra"
        }
    }

    fun updateInventoryName(name: String) {
        _inventory.value = _inventory.value.copy(InventoryName = name)
    }

    fun updatePrice(price: Double) {
        _inventory.value = _inventory.value.copy(Price = price)
    }

    fun updateColor(color: String) {
        _inventory.value = _inventory.value.copy(Color = color)
    }

    fun updateImage(image: String) {
        _inventory.value = _inventory.value.copy(IconFileName = image)
    }

    fun updateUnit(unit: Unit) {
        _inventory.value = _inventory.value.copy(UnitID = unit.UnitID, UnitName = unit.UnitName)
    }
}