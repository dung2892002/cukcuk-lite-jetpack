package com.example.cukcuk.presentation.ui.statistic.statistic_by_inventory

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cukcuk.domain.model.StatisticByInventory
import com.example.cukcuk.domain.usecase.statistic.GetStatisticByInventoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class StatisticByInventoryViewModel @Inject constructor(
    private val getStatisticByInventoryUseCase: GetStatisticByInventoryUseCase
) : ViewModel() {
    private val _statisticByInventory = mutableStateOf(listOf<StatisticByInventory>())
    val statisticByInventory: State<List<StatisticByInventory>> = _statisticByInventory

    private val _totalAmount = mutableDoubleStateOf(0.0)
    val totalAmount: State<Double> = _totalAmount

    fun handleStatistic(start: LocalDateTime, end: LocalDateTime) {
        viewModelScope.launch {
            _statisticByInventory.value = getStatisticByInventoryUseCase(start, end)
            _totalAmount.doubleValue = _statisticByInventory.value.sumOf { it.Amount }
        }
    }
}