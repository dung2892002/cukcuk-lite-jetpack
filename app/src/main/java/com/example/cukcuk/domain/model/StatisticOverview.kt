package com.example.cukcuk.domain.model

import java.time.LocalDateTime

data class StatisticOverview (
    var Amount: Double = 0.0,
    var Color: String = "",
    var IconFile: String = "",
    var Title: String = "",
    var TimeStart: LocalDateTime,
    var TimeEnd: LocalDateTime
)