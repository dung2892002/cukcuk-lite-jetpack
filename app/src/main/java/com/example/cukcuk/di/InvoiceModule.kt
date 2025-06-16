package com.example.cukcuk.di

import android.content.Context
import com.example.data.local.dao.InventoryDao
import com.example.data.local.dao.InvoiceDao
import com.example.data.repository.InventoryRepositoryImpl
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
class InvoiceModule {

    @Provides
    @Singleton
    fun provideInvoiceDao(
        @ApplicationContext context: Context) : InvoiceDao {
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


    @Provides
    @Singleton
    fun provideCreateInvoiceUseCase(
        repository: InvoiceRepository,
        syncHelper: SynchronizeHelper
    ): CreateInvoiceUseCase {
        return CreateInvoiceUseCase(repository, syncHelper)
    }

    @Provides
    @Singleton
    fun provideDeleteInvoiceUseCase(
        repository: InvoiceRepository,
        syncHelper: SynchronizeHelper
    ): DeleteInvoiceUseCase {
        return DeleteInvoiceUseCase(repository, syncHelper)
    }

    @Provides
    @Singleton
    fun provideGetInventorySelectUseCase(
        repository: InvoiceRepository
    ): GetInventorySelectUseCase {
        return GetInventorySelectUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetInvoiceDataToPaymentUseCase(
        repository: InvoiceRepository
    ): GetInvoiceDataToPaymentUseCase {
        return GetInvoiceDataToPaymentUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetInvoiceDetailUseCase(
        repository: InvoiceRepository
    ): GetInvoiceDetailUseCase {
        return GetInvoiceDetailUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetInvoicesNotPaymentUseCase(
        repository: InvoiceRepository
    ): GetInvoicesNotPaymentUseCase {
        return GetInvoicesNotPaymentUseCase(repository)
    }

    @Provides
    @Singleton
    fun providePaymentInvoiceUseCase(
        repository: InvoiceRepository,
        syncHelper: SynchronizeHelper
    ): PaymentInvoiceUseCase {
        return PaymentInvoiceUseCase(repository, syncHelper)
    }

    @Provides
    @Singleton
    fun provideUpdateInvoiceUseCase(
        repository: InvoiceRepository,
        syncHelper: SynchronizeHelper
    ): UpdateInvoiceUseCase {
        return UpdateInvoiceUseCase(repository, syncHelper)
    }
}