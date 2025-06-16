package com.example.cukcuk.di.statistic

import com.example.domain.repository.StatisticRepository
import com.example.domain.usecase.statistic.GetStatisticByInventoryUseCase
import com.example.domain.usecase.statistic.GetStatisticByTimeUseCase
import com.example.domain.usecase.statistic.GetStatisticOverviewUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {
    @Provides
    fun provideGetStatisticByInventoryUseCase(
        repository: StatisticRepository
    ): GetStatisticByInventoryUseCase {
        return GetStatisticByInventoryUseCase(repository)
    }

    @Provides
    fun provideGetStatisticOverviewUseCase(
        repository: StatisticRepository
    ): GetStatisticOverviewUseCase {
        return GetStatisticOverviewUseCase(repository)
    }

    @Provides
    fun provideGetStatisticByTimeUseCase(
        repository: StatisticRepository
    ): GetStatisticByTimeUseCase {
        return GetStatisticByTimeUseCase(repository)
    }
}