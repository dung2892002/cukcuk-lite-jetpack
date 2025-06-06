package com.example.cukcuk.domain.usecase.statistic

import com.example.cukcuk.domain.dtos.StatisticOverview
import com.example.cukcuk.domain.repository.StatisticRepository
import javax.inject.Inject

class GetStatisticOverviewUseCase @Inject constructor(
    private val repository: StatisticRepository
) {

    suspend operator fun invoke() : List<StatisticOverview> {
        return repository.getStatisticOverview()
    }
}