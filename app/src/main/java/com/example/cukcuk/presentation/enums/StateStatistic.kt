package com.example.cukcuk.presentation.enums

import androidx.annotation.StringRes
import com.example.cukcuk.utils.DateTimeHelper
import java.time.LocalDateTime
import com.example.cukcuk.R

enum class StateStatistic(
    @StringRes var title: Int,
    var timeRange: Pair<LocalDateTime, LocalDateTime>? = null
) {
    Overview(
        title = R.string.statistic_state_title_Overview,
    ),
    ThisWeek(
        title = R.string.statistic_state_title_ThisWeek,
        timeRange = DateTimeHelper.getThisWeek()
    ),
    ThisMonth(
        title = R.string.statistic_state_title_ThisMonth,
        timeRange = DateTimeHelper.getThisMonth()
    ),

    ThisYear(
        title = R.string.statistic_state_title_ThisYear,
        timeRange = DateTimeHelper.getThisYear()
    ),

    LastWeek(
        title = R.string.statistic_state_title_LastWeek,
        timeRange = DateTimeHelper.getLastWeek()
    ),

    LastMonth(
        title = R.string.statistic_state_title_LastMonth,
        timeRange = DateTimeHelper.getLastMonth()
        ),

    LastYear(
        title = R.string.statistic_state_title_LastYear,
        timeRange = DateTimeHelper.getLastYear()
    ),

    Other(
        title = R.string.statistic_state_title_Other
    )
}