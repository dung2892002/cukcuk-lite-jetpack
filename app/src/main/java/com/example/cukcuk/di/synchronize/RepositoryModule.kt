package com.example.cukcuk.di.synchronize

import android.content.Context
import com.example.data.local.dao.SynchronizeDao
import com.example.data.repository.SynchronizeRepositoryImpl
import com.example.domain.repository.SynchronizeRepository
import com.example.domain.usecase.synchronize.GetCountSyncUseCase
import com.example.domain.usecase.synchronize.GetLastSyncTimeUseCase
import com.example.domain.usecase.synchronize.UpdateSyncDataUseCase
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
    fun provideSynchronizeDao(
        @ApplicationContext context: Context
    ) : SynchronizeDao {
        return SynchronizeDao(context)
    }

    @Provides
    @Singleton
    fun provideRepositoryImpl(dao: SynchronizeDao) : SynchronizeRepositoryImpl {
        return SynchronizeRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideSynchronizeRepository(
        repository: SynchronizeRepositoryImpl
    ): SynchronizeRepository {
        return repository
    }

    @Provides
    fun provideSynchronizeHelper(
        repository: SynchronizeRepository
    ): SynchronizeHelper {
        return SynchronizeHelper(repository)
    }
}