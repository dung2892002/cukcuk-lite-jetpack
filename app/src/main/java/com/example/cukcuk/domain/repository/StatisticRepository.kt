package com.example.cukcuk.domain.repository

import com.example.cukcuk.domain.dtos.StatisticByInventory
import com.example.cukcuk.domain.dtos.StatisticByTime
import com.example.cukcuk.domain.dtos.StatisticOverview
import java.time.LocalDateTime

interface StatisticRepository {
    fun getStatisticOverview(): List<StatisticOverview>
    fun getDailyStatisticOfWeek(start: LocalDateTime, end: LocalDateTime): List<StatisticByTime>
    fun getDailyStatisticOfMonth(start: LocalDateTime, end: LocalDateTime): List<StatisticByTime>
    fun getMonthlyStatistic(start: LocalDateTime, end: LocalDateTime): List<StatisticByTime>
    fun getStatisticByInventory(start: LocalDateTime, end: LocalDateTime): List<StatisticByInventory>
}