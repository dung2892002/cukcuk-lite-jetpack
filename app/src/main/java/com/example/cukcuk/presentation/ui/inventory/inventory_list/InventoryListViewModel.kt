package com.example.cukcuk.presentation.ui.inventory.inventory_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cukcuk.domain.model.Inventory
import com.example.cukcuk.domain.usecase.inventory.GetInventoryListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryListViewModel @Inject constructor(
    private val getInventoryListUseCase: GetInventoryListUseCase
) :ViewModel() {
    private val _inventories = mutableStateOf<List<Inventory>>(emptyList())
    val inventories: State<List<Inventory>> = _inventories

    init {
        viewModelScope.launch {
            loadInventories()
        }
    }

    suspend fun loadInventories() {
        val data = getInventoryListUseCase.invoke()
        _inventories.value = data
    }

}