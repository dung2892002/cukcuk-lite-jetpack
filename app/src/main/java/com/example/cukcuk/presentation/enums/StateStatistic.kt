package com.example.cukcuk.presentation.enums

import com.example.cukcuk.utils.DateTimeHelper
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.TemporalAdjusters

enum class StateStatistic(
    var title: String,
    var timeRange: Pair<LocalDateTime, LocalDateTime>? = null
) {
    Yesterday(
        title = "Hôm qua",
        timeRange = DateTimeHelper.getYesterday()
    ),
    Today(
        title = "Hôm nay",
        timeRange = DateTimeHelper.getToday()
    ),
    Overview(
        title = "Gần đây",
    ),
    ThisWeek(
        title = "Tuần này",
        timeRange = DateTimeHelper.getThisWeek()
    ),
    ThisMonth(
        title = "Tháng này",
        timeRange = DateTimeHelper.getThisMonth()
    ),

    ThisYear(
        title = "Năm nay",
        timeRange = DateTimeHelper.getThisYear()
    ),

    LastWeek(
        title = "Tuần trước",
        timeRange = DateTimeHelper.getLastWeek()
    ),

    LastMonth(
        title = "Tháng trước",
        timeRange = DateTimeHelper.getLastMonth()
        ),

    LastYear(
        title = "Năm trước",
        timeRange = DateTimeHelper.getLastYear()
    ),

    Other(
        title = "Khác"
    )
}