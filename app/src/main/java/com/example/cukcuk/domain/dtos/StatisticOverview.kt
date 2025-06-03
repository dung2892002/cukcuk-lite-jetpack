package com.example.cukcuk.domain.dtos

import com.example.cukcuk.presentation.enums.StateStatistic

data class StatisticOverview (
    var Amount: Double = 0.0,
    var Color: String = "",
    var IconFile: String = "",
    var StatisticState: StateStatistic
)