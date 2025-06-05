package com.example.cukcuk.di

import com.example.cukcuk.data.repository.SynchronizeRepositoryImpl
import com.example.cukcuk.domain.repository.SynchronizeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class SynchronizeModule {
    @Binds
    @Singleton
    abstract fun bindSynchronizeRepository(
        impl: SynchronizeRepositoryImpl
    ): SynchronizeRepository
}