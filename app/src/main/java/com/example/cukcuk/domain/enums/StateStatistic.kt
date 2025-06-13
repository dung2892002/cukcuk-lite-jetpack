package com.example.cukcuk.domain.enums

import com.example.cukcuk.utils.DateTimeHelper
import java.time.LocalDateTime

enum class StateStatistic() {
    Overview,
    ThisWeek,
    ThisMonth,
    ThisYear,
    LastWeek,
    LastMonth,
    LastYear,
    Other
}


fun StateStatistic.getTimeRange(): Pair<LocalDateTime, LocalDateTime>? {
    return when (this) {
        StateStatistic.ThisWeek -> DateTimeHelper.getThisWeek()
        StateStatistic.ThisMonth -> DateTimeHelper.getThisMonth()
        StateStatistic.ThisYear -> DateTimeHelper.getThisYear()
        StateStatistic.LastWeek -> DateTimeHelper.getLastWeek()
        StateStatistic.LastMonth -> DateTimeHelper.getLastMonth()
        StateStatistic.LastYear -> DateTimeHelper.getLastYear()
        else -> null
    }
}