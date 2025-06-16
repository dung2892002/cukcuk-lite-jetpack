package com.example.domain.usecase.statistic

import com.example.domain.model.StatisticByInventory
import com.example.domain.repository.StatisticRepository
import java.time.LocalDateTime

class GetStatisticByInventoryUseCase (
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(start: LocalDateTime, end: LocalDateTime): List<StatisticByInventory> {
        return repository.getStatisticByInventory(start, end)
    }
}