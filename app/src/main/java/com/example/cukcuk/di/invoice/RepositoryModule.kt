package com.example.cukcuk.di.invoice

import android.content.Context
import com.example.data.local.dao.InvoiceDao
import com.example.data.repository.InvoiceRepositoryImpl
import com.example.domain.repository.InvoiceRepository
import com.example.domain.usecase.invoice.CreateInvoiceUseCase
import com.example.domain.usecase.invoice.DeleteInvoiceUseCase
import com.example.domain.usecase.invoice.GetInventorySelectUseCase
import com.example.domain.usecase.invoice.GetInvoiceDataToPaymentUseCase
import com.example.domain.usecase.invoice.GetInvoiceDetailUseCase
import com.example.domain.usecase.invoice.GetInvoicesNotPaymentUseCase
import com.example.domain.usecase.invoice.PaymentInvoiceUseCase
import com.example.domain.usecase.invoice.UpdateInvoiceUseCase
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
    fun provideInvoiceDao(
        @ApplicationContext context: Context
    ) : InvoiceDao {
        return InvoiceDao(context)
    }

    @Provides
    @Singleton
    fun provideRepositoryImpl(dao: InvoiceDao) : InvoiceRepositoryImpl {
        return InvoiceRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideInvoiceRepository(
        repository: InvoiceRepositoryImpl
    ): InvoiceRepository {
        return repository
    }
}