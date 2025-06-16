package com.example.cukcuk.di.inventory

import android.content.Context
import com.example.data.local.dao.InventoryDao
import com.example.data.repository.InventoryRepositoryImpl
import com.example.domain.repository.InventoryRepository
import com.example.domain.usecase.inventory.CreateInventoryUseCase
import com.example.domain.usecase.inventory.DeleteInventoryUseCase
import com.example.domain.usecase.inventory.GetInventoryDetailUseCase
import com.example.domain.usecase.inventory.GetInventoryListUseCase
import com.example.domain.usecase.inventory.UpdateInventoryUseCase
import com.example.domain.utils.SynchronizeHelper
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
    fun provideInventoryDao(
        @ApplicationContext context: Context
    ) : InventoryDao {
        return InventoryDao(context)
    }

    @Provides
    @Singleton
    fun provideRepositoryImpl(dao: InventoryDao) : InventoryRepositoryImpl {
        return InventoryRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideInventoryRepository(
        repository: InventoryRepositoryImpl
    ): InventoryRepository {
        return repository
    }
}