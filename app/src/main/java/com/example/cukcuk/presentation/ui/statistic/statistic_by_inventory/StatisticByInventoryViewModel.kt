package com.example.cukcuk.presentation.ui.statistic.statistic_by_inventory

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cukcuk.domain.dtos.StatisticByInventory
import com.example.cukcuk.domain.usecase.statistic.GetStatisticByInventoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class StatisticByInventoryViewModel @Inject constructor(
    private val getStatisticByInventoryUseCase: GetStatisticByInventoryUseCase
) : ViewModel() {
    private val _statisticByInventory = mutableStateOf(listOf<StatisticByInventory>())
    val statisticByInventory: State<List<StatisticByInventory>> = _statisticByInventory

    fun handleStatistic(start: LocalDateTime, end: LocalDateTime) {
        _statisticByInventory.value = getStatisticByInventoryUseCase(start, end)
    }
}