package com.example.cukcuk.di

import android.content.Context
import com.example.cukcuk.data.local.dao.UnitDao
import com.example.cukcuk.data.repository.UnitRepositoryImpl
import com.example.cukcuk.domain.repository.UnitRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UnitModule {

    @Provides
    @Singleton
    fun provideUnitRepository(dao: UnitDao): UnitRepository {
        return UnitRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideUnitDao(@ApplicationContext context: Context): UnitDao {
        return UnitDao(context)
    }
}