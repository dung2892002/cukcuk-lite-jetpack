package com.example.data.repository

import com.example.data.local.dao.StatisticDao
import com.example.domain.model.StatisticByInventory
import com.example.domain.model.StatisticByTime
import com.example.domain.model.StatisticOverview
import com.example.domain.repository.StatisticRepository
import com.example.domain.utils.DateTimeHelper
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter


class StatisticRepositoryImpl (
    private val dao: StatisticDao
) : StatisticRepository {

    override suspend fun getStatisticOverview(): List<StatisticOverview> {
        val result = dao.getStatisticOverview()

        val colors = listOf<String>("#5AB4FD", "#5AB4FD", "#4CAF50", "#F44336", "#2196F3")
        val icons = listOf<String>("ic-calendar-1.png", "ic-calendar-1.png", "ic-calendar-7.png", "ic-calendar-30.png", "ic-calendar-12.png")
        val titles = listOf<String>("Hôm qua", "Hôm nay", "Tuần này", "Tháng này", "Năm nay")
        val timeRanges = listOf<Pair<LocalDateTime, LocalDateTime>>(
            DateTimeHelper.getYesterday(),
            DateTimeHelper.getToday(),
            DateTimeHelper.getThisWeek(),
            DateTimeHelper.getThisMonth(),
            DateTimeHelper.getThisYear()
        )

        val statistics = result.mapIndexed { index, item ->
            StatisticOverview(
                Amount = item.Amount,
                Color = colors[index],
                IconFile = icons[index],
                Title = titles[index],
                TimeStart = timeRanges[index].first,
                TimeEnd = timeRanges[index].second
            )
        }

        return statistics
    }

    override suspend fun getDailyStatisticOfWeek(
        start: LocalDateTime,
        end: LocalDateTime
    ): List<StatisticByTime> {
        val results = dao.getDailyStatisticOfWeek(start, end)
        val statistics = results.map { item ->
            StatisticByTime(
                Amount = item.TotalAmount,
                TimeStart = item.Day.atStartOfDay(),
                TimeEnd = item.Day.atTime(LocalTime.MAX),
                Title = when (item.Day.dayOfWeek) {
                    DayOfWeek.MONDAY -> "Thứ 2"
                    DayOfWeek.TUESDAY -> "Thứ 3"
                    DayOfWeek.WEDNESDAY -> "Thứ 4"
                    DayOfWeek.THURSDAY -> "Thứ 5"
                    DayOfWeek.FRIDAY -> "Thứ 6"
                    DayOfWeek.SATURDAY -> "Thứ 7"
                    DayOfWeek.SUNDAY -> "Chủ nhật"
                }
            )
        }

        return statistics.takeIf { it.sumOf { s -> s.Amount } > 0.0 } ?: emptyList()
    }

    override suspend fun getDailyStatisticOfMonth(
        start: LocalDateTime,
        end: LocalDateTime
    ): List<StatisticByTime> {
        val results = dao.getDailyStatisticOfMonth(start, end)

        val statistics = results.map { item ->
            StatisticByTime(
                Amount = item.TotalAmount,
                TimeStart = item.Day.atStartOfDay(),
                TimeEnd = item.Day.atTime(LocalTime.MAX),
                Title = "Ngày ${item.Day.dayOfMonth}"
            )
        }

        return statistics.takeIf { it.sumOf { s -> s.Amount } > 0.0 } ?: emptyList()
    }

    override suspend fun getMonthlyStatistic(
        start: LocalDateTime,
        end: LocalDateTime
    ): List<StatisticByTime> {
        val results = dao.getMonthlyStatistic(start, end)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM")

        val statistics = results.map { item ->
            val month = YearMonth.parse(item.Month, formatter)
            StatisticByTime(
                Amount = item.TotalAmount,
                TimeStart = month.atDay(1).atStartOfDay(),
                TimeEnd = month.atEndOfMonth().atTime(LocalTime.MAX),
                Title = "Tháng ${month.monthValue}"
            )
        }

        val totalAmount = statistics.sumOf { it.Amount }
        return if (totalAmount == 0.0) emptyList() else statistics
    }

    override suspend fun getStatisticByInventory(
        start: LocalDateTime,
        end: LocalDateTime
    ): List<StatisticByInventory> {
        var result = dao.getStatisticByInventory(start, end)
        if (result.isEmpty()) return emptyList()

        val totalAmount = result.sumOf { it.Amount }
        if (totalAmount == 0.0) return emptyList()

        var colors = listOf("#2196F3", "#4CAF50", "#F44336", "#FFC107", "#4B3FB5", "#001F54", "#BCBCBC")

        val statistics = result.mapIndexed { index, item ->
            StatisticByInventory(
                InventoryName = item.InventoryName,
                Quantity = item.Quantity,
                Amount = item.Amount,
                UnitName = item.UnitName,
                Percentage = item.Amount/ totalAmount * 100.0,
                Color = colors.getOrElse(index) { colors.last() },
                SortOrder = index + 1
            )
        }

        return statistics
    }

}