package com.example.domain.usecase.statistic

import com.example.domain.model.StatisticByTime
import com.example.domain.repository.StatisticRepository
import com.example.domain.enums.StateStatistic
import com.example.domain.enums.getTimeRange


class GetStatisticByTimeUseCase(
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(state: StateStatistic): List<StatisticByTime>? {
        val timeRange = state.getTimeRange()!!
        val start = timeRange.first
        val end = timeRange.second
        return when(state) {
            StateStatistic.ThisWeek -> repository.getDailyStatisticOfWeek(start, end)
            StateStatistic.ThisMonth -> repository.getDailyStatisticOfMonth(start, end)
            StateStatistic.ThisYear -> repository.getMonthlyStatistic(start, end)
            StateStatistic.LastWeek -> repository.getDailyStatisticOfWeek(start, end)
            StateStatistic.LastMonth -> repository.getDailyStatisticOfMonth(start, end)
            StateStatistic.LastYear -> repository.getMonthlyStatistic(start, end)
            else -> null
        }
    }
}