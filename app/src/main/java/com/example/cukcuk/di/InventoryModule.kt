package com.example.cukcuk.di

import android.content.Context
import com.example.cukcuk.data.local.dao.InventoryDao
import com.example.cukcuk.data.local.dao.UnitDao
import com.example.cukcuk.data.repository.InventoryRepositoryImpl
import com.example.cukcuk.data.repository.UnitRepositoryImpl
import com.example.cukcuk.domain.repository.InventoryRepository
import com.example.cukcuk.domain.repository.UnitRepository
import com.example.cukcuk.domain.usecase.inventory.GetInventoryListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class InventoryModule {
    @Provides
    @Singleton
    fun provideInventoryRepository(dao: InventoryDao): InventoryRepository {
        return InventoryRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideInventoryDao(@ApplicationContext context: Context): InventoryDao {
        return InventoryDao(context)
    }
}