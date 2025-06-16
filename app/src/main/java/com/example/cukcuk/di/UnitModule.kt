package com.example.cukcuk.di

import android.content.Context
import com.example.data.local.dao.InvoiceDao
import com.example.data.local.dao.UnitDao
import com.example.data.repository.InvoiceRepositoryImpl
import com.example.data.repository.UnitRepositoryImpl
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
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UnitModule {

    @Provides
    @Singleton
    fun provideUnitDao(
        @ApplicationContext context: Context) : UnitDao{
        return UnitDao(context)
    }

    @Provides
    @Singleton
    fun provideRepositoryImpl(dao: UnitDao) : UnitRepositoryImpl {
        return UnitRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideUnitRepository(
        repository: UnitRepositoryImpl
    ): UnitRepository {
        return repository
    }

    @Provides
    @Singleton
    fun provideCreateUnitUseCase(
        repository: UnitRepository,
        syncHelper: SynchronizeHelper
    ): CreateUnitUseCase {
        return CreateUnitUseCase(repository, syncHelper)
    }

    @Provides
    @Singleton
    fun provideDeleteUnitUseCase(
        repository: UnitRepository,
        syncHelper: SynchronizeHelper
    ): DeleteUnitUseCase {
        return DeleteUnitUseCase(repository, syncHelper)
    }

    @Provides
    @Singleton
    fun provideGetUnitDetailUseCase(
        repository: UnitRepository
    ): GetUnitDetailUseCase {
        return GetUnitDetailUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllUnitUseCase(
        repository: UnitRepository
    ): GetAllUnitUseCase {
        return GetAllUnitUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateUnitUseCase(
        repository: UnitRepository,
        syncHelper: SynchronizeHelper
    ): UpdateUnitUseCase {
        return UpdateUnitUseCase(repository, syncHelper)
    }
}