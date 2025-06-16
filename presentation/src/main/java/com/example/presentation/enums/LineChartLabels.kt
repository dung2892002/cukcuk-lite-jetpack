package com.example.presentation.enums

import androidx.annotation.ArrayRes
import androidx.annotation.StringRes
import com.example.presentation.R

enum class LineChartLabels(
    @ArrayRes val labels: Int,
    @StringRes val xAxisLabel: Int,
) {
    DAY_IN_WEEK(
        labels = R.array.day_in_week,
        xAxisLabel = R.string.xAxisLabel_day
    ),

    DAY_IN_MONTH(
        labels = R.array.day_in_month,
        xAxisLabel = R.string.xAxisLabel_day
    ),

    MONTH_IN_YEAR(
        labels = R.array.month_in_year,
        xAxisLabel = R.string.xAxisLabel_day
    )

}