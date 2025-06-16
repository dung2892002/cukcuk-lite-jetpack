package com.example.domain.usecase.statistic

import com.example.domain.model.StatisticOverview
import com.example.domain.repository.StatisticRepository

class GetStatisticOverviewUseCase (
    private val repository: StatisticRepository
) {

    suspend operator fun invoke() : List<StatisticOverview> {
        return repository.getStatisticOverview()
    }
}