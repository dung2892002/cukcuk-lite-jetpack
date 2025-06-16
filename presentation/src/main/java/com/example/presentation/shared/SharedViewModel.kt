package com.example.presentation.shared

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.presentation.ui.statistic.statistic_by_inventory.RequestStatisticByInventory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {
    private val _requestStatisticByInventory = mutableStateOf<RequestStatisticByInventory?>(null)
    val requestStatisticByInventory: State<RequestStatisticByInventory?> = _requestStatisticByInventory

    private val _isShowOverviewStatistic = mutableStateOf(false)
    val isShowOverviewStatistic: State<Boolean> = _isShowOverviewStatistic

    fun setRequestStatisticByInventory(request: RequestStatisticByInventory) {
        _requestStatisticByInventory.value = request
    }

    fun setShowOverviewStatistic(isShow: Boolean) {
        _isShowOverviewStatistic.value = isShow
    }
}