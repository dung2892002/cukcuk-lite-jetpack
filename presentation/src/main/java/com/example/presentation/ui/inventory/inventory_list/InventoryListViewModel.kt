package com.example.presentation.ui.inventory.inventory_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Inventory
import com.example.domain.usecase.inventory.GetInventoryListUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class InventoryListViewModel (
    private val getInventoryListUseCase: GetInventoryListUseCase
) :ViewModel() {
    private val _inventories = mutableStateOf<List<Inventory>>(emptyList())
    val inventories: State<List<Inventory>> = _inventories

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    init {
        loadInventories()
    }

    fun loadInventories() {
        viewModelScope.launch {
            try {
                _loading.value = true
                delay(200)
                val data = getInventoryListUseCase.invoke()
                _inventories.value = data
            }
            catch (ex: Exception) {
                println(ex.message)
                ex.printStackTrace()
            }
            finally {
                _loading.value = false
            }
        }
    }
}