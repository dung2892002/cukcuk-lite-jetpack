package com.example.cukcuk.presentation.shared

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cukcuk.domain.dtos.RequestStatisticByInventory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {
    private val _requestStatisticByInventory = mutableStateOf<RequestStatisticByInventory?>(null)
    val requestStatisticByInventory: State<RequestStatisticByInventory?> = _requestStatisticByInventory

    fun setRequestStatisticByInventory(request: RequestStatisticByInventory) {
        _requestStatisticByInventory.value = request
    }

    fun clearData() {
        _requestStatisticByInventory.value = null
    }
}