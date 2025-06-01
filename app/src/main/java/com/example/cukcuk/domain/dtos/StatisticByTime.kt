package com.example.cukcuk.domain.dtos

import java.time.LocalDateTime


data class StatisticByTime (
    var Title: String = "",
    var Amount: Double = 0.0,
    var TimeStart: LocalDateTime,
    var TimeEnd: LocalDateTime
)