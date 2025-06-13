package com.example.cukcuk.domain.usecase.statistic

import com.example.cukcuk.domain.model.StatisticByTime
import com.example.cukcuk.domain.repository.StatisticRepository
import com.example.cukcuk.domain.enums.StateStatistic
import com.example.cukcuk.domain.enums.getTimeRange
import javax.inject.Inject


class GetStatisticByTimeUseCase @Inject constructor(
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