package com.example.cukcuk.di.inventory

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
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {
    @Provides
    fun provideCreateInventoryUseCase(
        inventoryRepository: InventoryRepository,
        syncHelper: SynchronizeHelper
    ): CreateInventoryUseCase {
        return CreateInventoryUseCase(inventoryRepository, syncHelper)
    }

    @Provides
    fun provideUpdateInventoryUseCase(
        inventoryRepository: InventoryRepository,
        syncHelper: SynchronizeHelper
    ): UpdateInventoryUseCase {
        return UpdateInventoryUseCase(inventoryRepository, syncHelper)
    }

    @Provides
    fun provideGetInventoryDetailUseCase(
        inventoryRepository: InventoryRepository
    ): GetInventoryDetailUseCase {
        return GetInventoryDetailUseCase(inventoryRepository)
    }

    @Provides
    fun provideGetInventoryListUseCase(
        inventoryRepository: InventoryRepository
    ): GetInventoryListUseCase {
        return GetInventoryListUseCase(inventoryRepository)
    }

    @Provides
    fun provideDeleteInventoryUseCase(
        inventoryRepository: InventoryRepository,
        syncHelper: SynchronizeHelper
    ): DeleteInventoryUseCase {
        return DeleteInventoryUseCase(inventoryRepository, syncHelper)
    }
}