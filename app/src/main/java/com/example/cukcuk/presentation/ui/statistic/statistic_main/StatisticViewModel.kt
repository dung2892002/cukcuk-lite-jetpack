package com.example.cukcuk.presentation.ui.statistic.statistic_main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cukcuk.domain.dtos.RequestStatisticByInventory
import com.example.cukcuk.domain.dtos.StatisticByInventory
import com.example.cukcuk.domain.dtos.StatisticByTime
import com.example.cukcuk.domain.dtos.StatisticOverview
import com.example.cukcuk.domain.usecase.statistic.GetStatisticByInventoryUseCase
import com.example.cukcuk.domain.usecase.statistic.GetStatisticByTimeUseCase
import com.example.cukcuk.domain.usecase.statistic.GetStatisticOverviewUseCase
import com.example.cukcuk.presentation.enums.LineChartLabels
import com.example.cukcuk.presentation.enums.StateStatistic
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class StatisticViewModel @Inject constructor(
    private val getStatisticByTimeUseCase: GetStatisticByTimeUseCase,
    private val getStatisticOverviewUseCase: GetStatisticOverviewUseCase,
    private val getStatisticByInventoryUseCase: GetStatisticByInventoryUseCase
) : ViewModel() {

    private val _title = mutableStateOf(StateStatistic.Overview.title)
    val title: State<String> = _title

    private val _currentState = mutableStateOf(StateStatistic.Overview)
    val currentState: State<StateStatistic> = _currentState

    private val _statisticOverview = mutableStateOf(listOf<StatisticOverview>())
    val statisticOverview: State<List<StatisticOverview>> = _statisticOverview

    private val _statisticByTime = mutableStateOf(listOf<StatisticByTime>())
    val statisticByTime: State<List<StatisticByTime>> = _statisticByTime

    private val _statisticByInventory = mutableStateOf(listOf<StatisticByInventory>())
    val statisticByInventory: State<List<StatisticByInventory>> = _statisticByInventory

    private val _showDialogSelectState = mutableStateOf(false)
    val showDialogSelectState: State<Boolean> = _showDialogSelectState

    private val _showDialogSelectTime = mutableStateOf(false)
    val showDialogSelectTime: State<Boolean> = _showDialogSelectTime

    private val _totalAmount = mutableDoubleStateOf(0.0)
    val totalAmount: State<Double> = _totalAmount

    private val _lineChartLabel = mutableStateOf(LineChartLabels.DAY_IN_WEEK)
    val lineChartLabels: State<LineChartLabels> = _lineChartLabel

    fun changeState(state: StateStatistic) {
        if (state != StateStatistic.Other){
            _title.value = state.title
            _currentState.value = state
        }
        closeDialogSelectState()
        when (state) {
            StateStatistic.Overview -> {
                getStatisticOverview()
            }
            StateStatistic.Other -> {
                openDialogSelectTime()
            }
            else -> getStatisticByTime(state)
        }
    }

    fun createRequestByStatisticOverview(item: StatisticOverview) : RequestStatisticByInventory {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val request = RequestStatisticByInventory()

        request.start = item.StatisticState.timeRange!!.first
        request.end = item.StatisticState.timeRange!!.second

        request.title = item.StatisticState.title + " (${request.start.format(formatter)})"
        return request
    }

    fun createRequestByStatisticTime(item: StatisticByTime) : RequestStatisticByInventory {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val request = RequestStatisticByInventory()

        request.start = item.TimeStart
        request.end = item.TimeEnd

        if (currentState.value == StateStatistic.ThisWeek || currentState.value == StateStatistic.LastWeek) {
            request.title = item.Title + " (${item.TimeStart.format(formatter)} )"
        }

        if (currentState.value == StateStatistic.ThisMonth || currentState.value == StateStatistic.LastMonth) {
            request.title = item.TimeStart.format(formatter)
        }

        if (currentState.value == StateStatistic.ThisYear || currentState.value == StateStatistic.LastYear) {
            request.title = item.Title + " (${item.TimeStart.format(formatter)} - ${item.TimeEnd.format(formatter)})"
        }
        return request
    }

    fun getStatisticOverview()  {
        _statisticOverview.value = getStatisticOverviewUseCase()
    }

    fun getStatisticByTime(state: StateStatistic) {
        _statisticByTime.value = getStatisticByTimeUseCase(state)!!
        when(state) {
            StateStatistic.ThisWeek -> setLineChartLabels(LineChartLabels.DAY_IN_WEEK)
            StateStatistic.LastWeek -> setLineChartLabels(LineChartLabels.DAY_IN_WEEK)
            StateStatistic.ThisMonth -> setLineChartLabels(LineChartLabels.DAY_IN_MONTH)
            StateStatistic.LastMonth -> setLineChartLabels(LineChartLabels.DAY_IN_MONTH)
            StateStatistic.ThisYear -> setLineChartLabels(LineChartLabels.MONTH_IN_YEAR)
            else -> setLineChartLabels(LineChartLabels.MONTH_IN_YEAR)
        }
    }

    fun setLineChartLabels(label: LineChartLabels) {
        _lineChartLabel.value = label
    }

    fun getStatisticByInventory(start: LocalDateTime, end: LocalDateTime) {
        _statisticByInventory.value = getStatisticByInventoryUseCase(start, end)
        _totalAmount.doubleValue = _statisticByInventory.value.sumOf { it.Amount }
    }

    fun openDialogSelectState() {
        _showDialogSelectState.value = true
    }

    fun closeDialogSelectState() {
        _showDialogSelectState.value = false
    }

    fun openDialogSelectTime() {
        _showDialogSelectTime.value = true
    }

    fun closeDialogSelectTime() {
        _showDialogSelectTime.value = false
    }

    fun handleStatisticByInventory(start: LocalDateTime, end: LocalDateTime) {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        getStatisticByInventory(start, end)
        if (start.toLocalDate() == end.toLocalDate())
            _title.value = "${start.format(formatter)}"
        else
            _title.value = "Từ ${start.format(formatter)} - ${end.format(formatter)}"
        _currentState.value = StateStatistic.Other
        closeDialogSelectTime()
    }
}
