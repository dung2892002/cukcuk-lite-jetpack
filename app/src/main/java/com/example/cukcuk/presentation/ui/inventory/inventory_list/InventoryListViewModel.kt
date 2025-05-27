package com.example.cukcuk.presentation.ui.inventory.inventory_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cukcuk.domain.model.Inventory
import com.example.cukcuk.domain.usecase.inventory.GetInventoryListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class InventoryListViewModel @Inject constructor(
    private val getInventoryListUseCase: GetInventoryListUseCase
) :ViewModel() {
    private val _inventories = mutableStateOf<List<Inventory>>(emptyList())
    val inventories: State<List<Inventory>> = _inventories

    init {
        loadInventories()
    }

    fun loadInventories() {
        viewModelScope.launch {
            val data = getInventoryListUseCase()
            _inventories.value = data
        }
    }

}