package com.example.cukcuk.domain.dtos

import java.time.LocalDateTime

data class RequestStatisticByInventory (
    var title: String = "",
    var start: LocalDateTime = LocalDateTime.now(),
    var end: LocalDateTime = LocalDateTime.now()
)