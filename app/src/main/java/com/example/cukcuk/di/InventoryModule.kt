package com.example.cukcuk.di

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
class InventoryModule {

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

    @Provides
    @Singleton
    fun provideCreateInventoryUseCase(
        inventoryRepository: InventoryRepository,
        syncHelper: SynchronizeHelper
    ): CreateInventoryUseCase {
        return CreateInventoryUseCase(inventoryRepository, syncHelper)
    }

    @Provides
    @Singleton
    fun provideUpdateInventoryUseCase(
        inventoryRepository: InventoryRepository,
        syncHelper: SynchronizeHelper
    ): UpdateInventoryUseCase {
        return UpdateInventoryUseCase(inventoryRepository, syncHelper)
    }

    @Provides
    @Singleton
    fun provideGetInventoryDetailUseCase(
        inventoryRepository: InventoryRepository
    ): GetInventoryDetailUseCase {
        return GetInventoryDetailUseCase(inventoryRepository)
    }

    @Provides
    @Singleton
    fun provideGetInventoryListUseCase(
        inventoryRepository: InventoryRepository
    ): GetInventoryListUseCase {
        return GetInventoryListUseCase(inventoryRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteInventoryUseCase(
        inventoryRepository: InventoryRepository,
        syncHelper: SynchronizeHelper
    ): DeleteInventoryUseCase {
        return DeleteInventoryUseCase(inventoryRepository, syncHelper)
    }
}