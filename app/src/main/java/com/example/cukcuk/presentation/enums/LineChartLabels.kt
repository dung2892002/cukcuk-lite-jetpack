package com.example.cukcuk.presentation.enums

enum class LineChartLabels(
    val labels: List<String>
) {
    DAY_IN_WEEK(
        labels = listOf("T2", "T3", "T4", "T5", "T6", "T7", "CN")
    ),

    DAY_IN_MONTH(
        labels = (1..31).map { it.toString()}
    ),

    MONTH_IN_YEAR(
        labels = listOf("T1", "T2", "T3", "T4", "T5", "T6", "T7", "T8", "T9", "T10", "T11", "T12")
    )

}