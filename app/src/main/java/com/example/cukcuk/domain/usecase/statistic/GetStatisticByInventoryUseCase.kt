package com.example.cukcuk.domain.usecase.statistic

import com.example.cukcuk.domain.model.StatisticByInventory
import com.example.cukcuk.domain.repository.StatisticRepository
import java.time.LocalDateTime
import javax.inject.Inject

class GetStatisticByInventoryUseCase @Inject constructor(
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(start: LocalDateTime, end: LocalDateTime): List<StatisticByInventory> {
        return repository.getStatisticByInventory(start, end)
    }
}