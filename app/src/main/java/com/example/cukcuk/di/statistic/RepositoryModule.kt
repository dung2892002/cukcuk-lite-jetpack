package com.example.cukcuk.di.statistic

import android.content.Context
import com.example.data.local.dao.StatisticDao
import com.example.data.repository.StatisticRepositoryImpl
import com.example.domain.repository.StatisticRepository
import com.example.domain.usecase.statistic.GetStatisticByInventoryUseCase
import com.example.domain.usecase.statistic.GetStatisticByTimeUseCase
import com.example.domain.usecase.statistic.GetStatisticOverviewUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideStatisticDao(
        @ApplicationContext context: Context
    ) : StatisticDao {
        return StatisticDao(context)
    }

    @Provides
    @Singleton
    fun provideRepositoryImpl(dao: StatisticDao) : StatisticRepositoryImpl {
        return StatisticRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideStatisticRepository(
        repository: StatisticRepositoryImpl
    ): StatisticRepository {
        return repository
    }
}