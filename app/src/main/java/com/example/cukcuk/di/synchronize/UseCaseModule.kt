package com.example.cukcuk.di.synchronize

import com.example.domain.repository.SynchronizeRepository
import com.example.domain.usecase.synchronize.GetCountSyncUseCase
import com.example.domain.usecase.synchronize.GetLastSyncTimeUseCase
import com.example.domain.usecase.synchronize.UpdateSyncDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {
    @Provides
    fun provideGetLastSyncTimeUseCase(
        repository: SynchronizeRepository
    ): GetLastSyncTimeUseCase {
        return GetLastSyncTimeUseCase(repository)
    }

    @Provides
    fun provideGetCountSyncUseCase(
        repository: SynchronizeRepository
    ): GetCountSyncUseCase {
        return GetCountSyncUseCase(repository)
    }

    @Provides
    fun provideUpdateSyncDataUseCase(
        repository: SynchronizeRepository
    ): UpdateSyncDataUseCase {
        return UpdateSyncDataUseCase(repository)
    }
}