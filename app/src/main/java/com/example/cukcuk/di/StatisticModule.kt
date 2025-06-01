package com.example.cukcuk.di

import com.example.cukcuk.data.repository.StatisticRepositoryImpl
import com.example.cukcuk.domain.repository.StatisticRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StatisticModule {

    @Binds
    @Singleton
    abstract fun bindStatisticRepository(
        impl: StatisticRepositoryImpl
    ) : StatisticRepository
}