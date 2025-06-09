package com.example.cukcuk.data.repository

import com.example.cukcuk.data.local.dao.StatisticDao
import com.example.cukcuk.domain.dtos.StatisticByInventory
import com.example.cukcuk.domain.dtos.StatisticByTime
import com.example.cukcuk.domain.dtos.StatisticOverview
import com.example.cukcuk.domain.repository.StatisticRepository
import com.example.cukcuk.presentation.enums.StateStatistic
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject


class StatisticRepositoryImpl @Inject constructor(
    private val dao: StatisticDao
) : StatisticRepository {
    override suspend fun getStatisticOverview(): List<StatisticOverview> {
        val result = dao.getStatisticOverview()

        var colors = listOf<String>("#5AB4FD", "#5AB4FD", "#4CAF50", "#F44336", "#2196F3")
        var icons = listOf<String>("ic-calendar-1.png", "ic-calendar-1.png", "ic-calendar-7.png", "ic-calendar-30.png", "ic-calendar-12.png")
        val statistics = mutableListOf<StatisticOverview>()

        for (i in 0 until result.size) {
            statistics.add(
                StatisticOverview(
                    Amount = result[i].second,
                    IconFile = icons[i],
                    Color = colors[i],
                    StatisticState = when (result[i].first) {
                        "Yesterday" -> StateStatistic.Yesterday
                        "Today" -> StateStatistic.Today
                        "ThisWeek" -> StateStatistic.ThisWeek
                        "ThisMonth" -> StateStatistic.ThisMonth
                        "ThisYear" -> StateStatistic.ThisYear
                        else -> StateStatistic.Yesterday
                    }
                )
            )
        }

        return statistics
    }

    override suspend fun getDailyStatisticOfWeek(
        start: LocalDateTime,
        end: LocalDateTime
    ): List<StatisticByTime> {
        val resultMap = dao.getDailyStatisticOfWeek(start, end)

        val statistics = mutableListOf<StatisticByTime>()
        val daysBetween = ChronoUnit.DAYS.between(start, end)
        for (i in 0..daysBetween) {
            val day = start.toLocalDate().plusDays(i.toLong())

            val title = when (day.dayOfWeek) {
                DayOfWeek.MONDAY -> "Thứ 2"
                DayOfWeek.TUESDAY -> "Thứ 3"
                DayOfWeek.WEDNESDAY -> "Thứ 4"
                DayOfWeek.THURSDAY -> "Thứ 5"
                DayOfWeek.FRIDAY -> "Thứ 6"
                DayOfWeek.SATURDAY -> "Thứ 7"
                DayOfWeek.SUNDAY -> "Chủ nhật"
            }

            statistics.add(
                StatisticByTime(
                    Title = title,
                    Amount = resultMap[day] ?: 0.0,
                    TimeStart = day.atStartOfDay(),
                    TimeEnd = day.atTime(LocalTime.MAX)
                )
            )
        }
        val totalAmount = statistics.sumOf { it.Amount }
        return if (totalAmount == 0.0) emptyList() else statistics
    }

    override suspend fun getDailyStatisticOfMonth(
        start: LocalDateTime,
        end: LocalDateTime
    ): List<StatisticByTime> {
        val startDate = start.toLocalDate()
        val endDate = end.toLocalDate()
        val resultMap = dao.getDailyStatisticOfMonth(start, end)

        val statistics = mutableListOf<StatisticByTime>()
        var currentDay = startDate

        while (!currentDay.isAfter(endDate)) {
            statistics.add(
                StatisticByTime(
                    Title = "Ngày ${currentDay.dayOfMonth}",
                    Amount = resultMap[currentDay] ?: 0.0,
                    TimeStart = currentDay.atStartOfDay(),
                    TimeEnd = currentDay.atTime(LocalTime.MAX)
                )
            )
            currentDay = currentDay.plusDays(1)
        }

        val totalAmount = statistics.sumOf { it.Amount }
        return if (totalAmount == 0.0) emptyList() else statistics
    }

    override suspend fun getMonthlyStatistic(
        start: LocalDateTime,
        end: LocalDateTime
    ): List<StatisticByTime> {
        val startDate = start.toLocalDate()
        val endDate = end.toLocalDate()

        val resultMap = dao.getMonthlyStatistic(start, end)
        val statistics = mutableListOf<StatisticByTime>()
        var current = startDate.withDayOfMonth(1)
        while (!current.isAfter(endDate)) {
            val firstDay = current.withDayOfMonth(1)
            val lastDay = firstDay.withDayOfMonth(firstDay.lengthOfMonth())
            val key = Pair(firstDay.year, firstDay.monthValue)

            statistics.add(
                StatisticByTime(
                    Title = "Tháng ${firstDay.monthValue}",
                    Amount = resultMap[key] ?: 0.0,
                    TimeStart = firstDay.atStartOfDay(),
                    TimeEnd = lastDay.atTime(LocalTime.MAX)
                )
            )
            current = current.plusMonths(1)
        }

        val totalAmount = statistics.sumOf { it.Amount }
        return if (totalAmount == 0.0) emptyList() else statistics
    }

    override suspend fun getStatisticByInventory(
        start: LocalDateTime,
        end: LocalDateTime
    ): List<StatisticByInventory> {
        var result = dao.getStatisticByInventory(start, end)

        var colors = listOf<String>("#2196F3", "#4CAF50", "#F44336", "#FFC107", "#4B3FB5", "#001F54", "#BCBCBC")
        var sortOrder = 1

        val statistics = mutableListOf<StatisticByInventory>()

        for (i in 0 until result.size) {
            statistics.add(
                StatisticByInventory(
                    InventoryName = result[i].InventoryName,
                    Quantity = result[i].Quantity,
                    Amount = result[i].Amount,
                    UnitName = result[i].UnitName,
                    Percentage = 0.0,
                    Color = if(sortOrder - 1 < colors.size) colors[sortOrder - 1] else colors.last(),
                    SortOrder = sortOrder++
                )
            )
        }
        val totalAmount = statistics.sumOf { it.Amount }
        if (totalAmount == 0.0) return emptyList()

        statistics.forEach {
            it.Percentage = it.Amount / totalAmount * 100.0
        }
        return statistics
    }
}