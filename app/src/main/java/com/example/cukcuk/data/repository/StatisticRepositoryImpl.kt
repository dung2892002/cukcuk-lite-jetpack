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
    override fun getStatisticOverview(): List<StatisticOverview> {
        return dao.getStatisticOverview()
    }

    override fun getDailyStatisticOfWeek(
        start: LocalDateTime,
        end: LocalDateTime
    ): List<StatisticByTime> {
        return dao.getDailyStatisticOfWeek(start, end)
    }

    override fun getDailyStatisticOfMonth(
        start: LocalDateTime,
        end: LocalDateTime
    ): List<StatisticByTime> {
        return dao.getDailyStatisticOfMonth(start, end)
    }

    override fun getMonthlyStatistic(
        start: LocalDateTime,
        end: LocalDateTime
    ): List<StatisticByTime> {
        return dao.getMonthlyStatistic(start, end)
    }

    override fun getStatisticByInventory(
        start: LocalDateTime,
        end: LocalDateTime
    ): List<StatisticByInventory> {
        return dao.getStatisticByInventory(start, end)
    }
}