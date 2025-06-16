package com.example.presentation.mapper

import androidx.annotation.StringRes
import com.example.domain.enums.StateStatistic
import com.example.presentation.R

@StringRes
fun StateStatistic.getTitleResId(): Int {
    return when (this) {
        StateStatistic.Overview -> R.string.statistic_state_title_Overview
        StateStatistic.ThisWeek -> R.string.statistic_state_title_ThisWeek
        StateStatistic.ThisMonth -> R.string.statistic_state_title_ThisMonth
        StateStatistic.ThisYear -> R.string.statistic_state_title_ThisYear
        StateStatistic.LastWeek -> R.string.statistic_state_title_LastWeek
        StateStatistic.LastMonth -> R.string.statistic_state_title_LastMonth
        StateStatistic.LastYear -> R.string.statistic_state_title_LastYear
        else -> R.string.statistic_state_title_Other
    }
}
