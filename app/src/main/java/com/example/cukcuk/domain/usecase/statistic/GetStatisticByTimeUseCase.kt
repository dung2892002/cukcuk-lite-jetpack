package com.example.cukcuk.domain.usecase.statistic

import com.example.cukcuk.domain.dtos.StatisticByTime
import com.example.cukcuk.domain.repository.StatisticRepository
import com.example.cukcuk.presentation.enums.StateStatistic
import javax.inject.Inject


class GetStatisticByTimeUseCase @Inject constructor(
    private val repository: StatisticRepository
) {
    operator fun invoke(state: StateStatistic): List<StatisticByTime>? {
        return when(state) {
            StateStatistic.ThisWeek -> repository.getDailyStatisticOfWeek(state.timeRange!!.first, state.timeRange!!.second)
            StateStatistic.ThisMonth -> repository.getDailyStatisticOfMonth(state.timeRange!!.first, state.timeRange!!.second)
            StateStatistic.ThisYear -> repository.getMonthlyStatistic(state.timeRange!!.first, state.timeRange!!.second)
            StateStatistic.LastWeek -> repository.getDailyStatisticOfWeek(state.timeRange!!.first, state.timeRange!!.second)
            StateStatistic.LastMonth -> repository.getDailyStatisticOfMonth(state.timeRange!!.first, state.timeRange!!.second)
            StateStatistic.LastYear -> repository.getMonthlyStatistic(state.timeRange!!.first, state.timeRange!!.second)
            else -> null
        }
    }
}