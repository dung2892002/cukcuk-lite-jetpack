package com.example.cukcuk.domain.repository

import com.example.cukcuk.domain.model.StatisticByInventory
import com.example.cukcuk.domain.model.StatisticByTime
import com.example.cukcuk.domain.model.StatisticOverview
import java.time.LocalDateTime

interface StatisticRepository {
    suspend fun getStatisticOverview(): List<StatisticOverview>

    suspend fun getDailyStatisticOfWeek(start: LocalDateTime, end: LocalDateTime): List<StatisticByTime>

    suspend fun getDailyStatisticOfMonth(start: LocalDateTime, end: LocalDateTime): List<StatisticByTime>

    suspend fun getMonthlyStatistic(start: LocalDateTime, end: LocalDateTime): List<StatisticByTime>

    suspend fun getStatisticByInventory(start: LocalDateTime, end: LocalDateTime): List<StatisticByInventory>
}