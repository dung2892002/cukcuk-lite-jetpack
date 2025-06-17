package com.example.presentation.ui.statistic.statistic

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.presentation.ui.statistic.statistic_by_inventory.RequestStatisticByInventory
import com.example.domain.model.StatisticByInventory
import com.example.domain.model.StatisticByTime
import com.example.domain.model.StatisticOverview
import com.example.domain.usecase.statistic.GetStatisticByInventoryUseCase
import com.example.domain.usecase.statistic.GetStatisticByTimeUseCase
import com.example.domain.usecase.statistic.GetStatisticOverviewUseCase
import com.example.presentation.enums.LineChartLabels
import com.example.domain.enums.StateStatistic
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class StatisticViewModel(
    private val getStatisticByTimeUseCase: GetStatisticByTimeUseCase,
    private val getStatisticOverviewUseCase: GetStatisticOverviewUseCase,
    private val getStatisticByInventoryUseCase: GetStatisticByInventoryUseCase
) : ViewModel() {

    private val _title = mutableStateOf("")
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

    fun changeState(state: StateStatistic, context: Context) {
        if (state != StateStatistic.Other){
            _currentState.value = state
        }
        closeDialogSelectState()
        when (state) {
            StateStatistic.Overview -> {
                getStatisticOverview(context)
            }
            StateStatistic.Other -> {
                openDialogSelectTime()
            }
            else -> getStatisticByTime(state, context)
        }
    }

    fun createRequestByStatisticOverview(item: StatisticOverview) : RequestStatisticByInventory {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val request = RequestStatisticByInventory()

        request.start = item.TimeStart
        request.end = item.TimeEnd

        request.title = item.Title + " (${request.start.format(formatter)})"
        return request
    }

    fun createRequestByStatisticTime(item: StatisticByTime) : RequestStatisticByInventory {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val request = RequestStatisticByInventory()

        request.start = item.TimeStart
        request.end = item.TimeEnd

        if (currentState.value == StateStatistic.ThisWeek || currentState.value == StateStatistic.LastWeek) {
            request.title = item.Title + " (${item.TimeStart.format(formatter)})"
        }

        if (currentState.value == StateStatistic.ThisMonth || currentState.value == StateStatistic.LastMonth) {
            request.title = item.TimeStart.format(formatter)
        }

        if (currentState.value == StateStatistic.ThisYear || currentState.value == StateStatistic.LastYear) {
            request.title = item.Title + " (${item.TimeStart.format(formatter)} - ${item.TimeEnd.format(formatter)})"
        }
        return request
    }

    fun getStatisticOverview(context: Context)  {
        viewModelScope.launch {
            val titles = context.resources.getStringArray(com.example.presentation.R.array.statistic_overview_title)
            val statisticResults = getStatisticOverviewUseCase()
            _statisticOverview.value = statisticResults.mapIndexed { index, item ->
                item.copy(
                    Title = getTitleStatistic(titles, index)
                )
            }
        }
    }

    fun getStatisticByTime(state: StateStatistic, context: Context) {
        viewModelScope.launch {
            val titles = when (state) {
                StateStatistic.LastWeek -> context.resources.getStringArray(com.example.presentation.R.array.statistic_time_week_title)
                StateStatistic.ThisWeek -> context.resources.getStringArray(com.example.presentation.R.array.statistic_time_week_title)
                StateStatistic.LastMonth -> context.resources.getStringArray(com.example.presentation.R.array.statistic_time_month_title)
                StateStatistic.ThisMonth -> context.resources.getStringArray(com.example.presentation.R.array.statistic_time_month_title)
                StateStatistic.LastYear -> context.resources.getStringArray(com.example.presentation.R.array.statistic_time_year_title)
                StateStatistic.ThisYear -> context.resources.getStringArray(com.example.presentation.R.array.statistic_time_year_title)
                else -> emptyArray()
            }

            val statisticResults = getStatisticByTimeUseCase(state)!!
            _statisticByTime.value = statisticResults.mapIndexed { index, item ->
                item.copy(
                    Title = getTitleStatistic(titles,index)
                )
            }

            when(state) {
                StateStatistic.ThisWeek -> setLineChartLabels(LineChartLabels.DAY_IN_WEEK)
                StateStatistic.LastWeek -> setLineChartLabels(LineChartLabels.DAY_IN_WEEK)
                StateStatistic.ThisMonth -> setLineChartLabels(LineChartLabels.DAY_IN_MONTH)
                StateStatistic.LastMonth -> setLineChartLabels(LineChartLabels.DAY_IN_MONTH)
                StateStatistic.ThisYear -> setLineChartLabels(LineChartLabels.MONTH_IN_YEAR)
                else -> setLineChartLabels(LineChartLabels.MONTH_IN_YEAR)
            }
        }
    }

    fun setLineChartLabels(labels: LineChartLabels) {
        _lineChartLabel.value = labels
    }

    fun getStatisticByInventory(start: LocalDateTime, end: LocalDateTime) {
        viewModelScope.launch {
            _statisticByInventory.value = getStatisticByInventoryUseCase(start, end)
            _totalAmount.doubleValue = _statisticByInventory.value.sumOf { it.Amount }
        }
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
            _title.value = "${start.format(formatter)} - ${end.format(formatter)}"
        _currentState.value = StateStatistic.Other
        closeDialogSelectTime()
    }

    fun getTitleStatistic(titles: Array<String>, index: Int): String {
        return titles.getOrNull(index) ?: ""
    }
}
