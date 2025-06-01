package com.example.cukcuk.di

import com.example.cukcuk.data.repository.UnitRepositoryImpl
import com.example.cukcuk.domain.repository.UnitRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UnitModule {

    @Binds
    @Singleton
    abstract fun bindUnitRepository(
        impl: UnitRepositoryImpl
    ): UnitRepository

}