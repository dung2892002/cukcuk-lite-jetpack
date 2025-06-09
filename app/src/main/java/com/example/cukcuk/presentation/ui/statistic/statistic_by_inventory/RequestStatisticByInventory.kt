package com.example.cukcuk.presentation.ui.statistic.statistic_by_inventory

import java.time.LocalDateTime

data class RequestStatisticByInventory (
    var title: String = "",
    var start: LocalDateTime = LocalDateTime.now(),
    var end: LocalDateTime = LocalDateTime.now()
)