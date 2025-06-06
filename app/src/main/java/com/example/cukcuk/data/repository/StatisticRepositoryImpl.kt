package com.example.cukcuk.data.repository

import com.example.cukcuk.data.local.dao.StatisticDao
import com.example.cukcuk.domain.dtos.StatisticByInventory
import com.example.cukcuk.domain.dtos.StatisticByTime
import com.example.cukcuk.domain.dtos.StatisticOverview
import com.example.cukcuk.domain.repository.StatisticRepository
import java.time.LocalDateTime
import javax.inject.Inject


class StatisticRepositoryImpl @Inject constructor(
    private val dao: StatisticDao
) : StatisticRepository {
    override suspend fun getStatisticOverview(): List<StatisticOverview> {
        return dao.getStatisticOverview()
    }

    override suspend fun getDailyStatisticOfWeek(
        start: LocalDateTime,
        end: LocalDateTime
    ): List<StatisticByTime> {
        return dao.getDailyStatisticOfWeek(start, end)
    }

    override suspend fun getDailyStatisticOfMonth(
        start: LocalDateTime,
        end: LocalDateTime
    ): List<StatisticByTime> {
        return dao.getDailyStatisticOfMonth(start, end)
    }

    override suspend fun getMonthlyStatistic(
        start: LocalDateTime,
        end: LocalDateTime
    ): List<StatisticByTime> {
        return dao.getMonthlyStatistic(start, end)
    }

    override suspend fun getStatisticByInventory(
        start: LocalDateTime,
        end: LocalDateTime
    ): List<StatisticByInventory> {
        return dao.getStatisticByInventory(start, end)
    }
}