package com.example.cukcuk.di.unit

import com.example.domain.repository.UnitRepository
import com.example.domain.usecase.unit.CreateUnitUseCase
import com.example.domain.usecase.unit.DeleteUnitUseCase
import com.example.domain.usecase.unit.GetAllUnitUseCase
import com.example.domain.usecase.unit.GetUnitDetailUseCase
import com.example.domain.usecase.unit.UpdateUnitUseCase
import com.example.domain.utils.SynchronizeHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {
    @Provides
    fun provideCreateUnitUseCase(
        repository: UnitRepository,
        syncHelper: SynchronizeHelper
    ): CreateUnitUseCase {
        return CreateUnitUseCase(repository, syncHelper)
    }

    @Provides
    fun provideDeleteUnitUseCase(
        repository: UnitRepository,
        syncHelper: SynchronizeHelper
    ): DeleteUnitUseCase {
        return DeleteUnitUseCase(repository, syncHelper)
    }

    @Provides
    fun provideGetUnitDetailUseCase(
        repository: UnitRepository
    ): GetUnitDetailUseCase {
        return GetUnitDetailUseCase(repository)
    }

    @Provides
    fun provideGetAllUnitUseCase(
        repository: UnitRepository
    ): GetAllUnitUseCase {
        return GetAllUnitUseCase(repository)
    }

    @Provides
    fun provideUpdateUnitUseCase(
        repository: UnitRepository,
        syncHelper: SynchronizeHelper
    ): UpdateUnitUseCase {
        return UpdateUnitUseCase(repository, syncHelper)
    }
}