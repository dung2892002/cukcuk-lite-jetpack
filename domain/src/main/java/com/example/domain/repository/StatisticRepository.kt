package com.example.domain.repository

import com.example.domain.model.StatisticByInventory
import com.example.domain.model.StatisticByTime
import com.example.domain.model.StatisticOverview
import java.time.LocalDateTime

interface StatisticRepository {
    suspend fun getStatisticOverview(): List<StatisticOverview>

    suspend fun getDailyStatisticOfWeek(start: LocalDateTime, end: LocalDateTime): List<StatisticByTime>

    suspend fun getDailyStatisticOfMonth(start: LocalDateTime, end: LocalDateTime): List<StatisticByTime>

    suspend fun getMonthlyStatistic(start: LocalDateTime, end: LocalDateTime): List<StatisticByTime>

    suspend fun getStatisticByInventory(start: LocalDateTime, end: LocalDateTime): List<StatisticByInventory>
}