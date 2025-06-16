package com.example.presentation.ui.inventory.inventory_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Inventory
import com.example.domain.usecase.inventory.GetInventoryListUseCase
import kotlinx.coroutines.launch

class InventoryListViewModel (
    private val getInventoryListUseCase: GetInventoryListUseCase
) :ViewModel() {
    private val _inventories = mutableStateOf<List<Inventory>>(emptyList())
    val inventories: State<List<Inventory>> = _inventories

    init {
        loadInventories()
    }

    fun loadInventories() {
        viewModelScope.launch {
            try {
                val data = getInventoryListUseCase.invoke()
                _inventories.value = data
            }
            catch (ex: Exception) {
                println(ex.message)
                ex.printStackTrace()
            }
        }
    }
}