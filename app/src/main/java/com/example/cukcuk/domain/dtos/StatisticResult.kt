package com.example.cukcuk.domain.dtos

data class StatisticResult(
    val TotalAmount: Double,
    val statisticsByTime: List<StatisticByTime>,
    val statisticsByInventory: List<StatisticByInventory>
)