package com.example.cukcuk.di.invoice

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
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {
    @Provides
    fun provideCreateInvoiceUseCase(
        repository: InvoiceRepository,
        syncHelper: SynchronizeHelper
    ): CreateInvoiceUseCase {
        return CreateInvoiceUseCase(repository, syncHelper)
    }

    @Provides
    fun provideDeleteInvoiceUseCase(
        repository: InvoiceRepository,
        syncHelper: SynchronizeHelper
    ): DeleteInvoiceUseCase {
        return DeleteInvoiceUseCase(repository, syncHelper)
    }

    @Provides
    fun provideGetInventorySelectUseCase(
        repository: InvoiceRepository
    ): GetInventorySelectUseCase {
        return GetInventorySelectUseCase(repository)
    }

    @Provides
    fun provideGetInvoiceDataToPaymentUseCase(
        repository: InvoiceRepository
    ): GetInvoiceDataToPaymentUseCase {
        return GetInvoiceDataToPaymentUseCase(repository)
    }

    @Provides
    fun provideGetInvoiceDetailUseCase(
        repository: InvoiceRepository
    ): GetInvoiceDetailUseCase {
        return GetInvoiceDetailUseCase(repository)
    }

    @Provides
    fun provideGetInvoicesNotPaymentUseCase(
        repository: InvoiceRepository
    ): GetInvoicesNotPaymentUseCase {
        return GetInvoicesNotPaymentUseCase(repository)
    }

    @Provides
    fun providePaymentInvoiceUseCase(
        repository: InvoiceRepository,
        syncHelper: SynchronizeHelper
    ): PaymentInvoiceUseCase {
        return PaymentInvoiceUseCase(repository, syncHelper)
    }

    @Provides
    fun provideUpdateInvoiceUseCase(
        repository: InvoiceRepository,
        syncHelper: SynchronizeHelper
    ): UpdateInvoiceUseCase {
        return UpdateInvoiceUseCase(repository, syncHelper)
    }
}