package com.example.cukcuk.di

import com.example.domain.usecase.inventory.CreateInventoryUseCase
import com.example.domain.usecase.inventory.DeleteInventoryUseCase
import com.example.domain.usecase.inventory.GetInventoryDetailUseCase
import com.example.domain.usecase.inventory.GetInventoryListUseCase
import com.example.domain.usecase.inventory.UpdateInventoryUseCase
import com.example.domain.usecase.invoice.CreateInvoiceUseCase
import com.example.domain.usecase.invoice.DeleteInvoiceUseCase
import com.example.domain.usecase.invoice.GetInventorySelectUseCase
import com.example.domain.usecase.invoice.GetInvoiceDataToPaymentUseCase
import com.example.domain.usecase.invoice.GetInvoiceDetailUseCase
import com.example.domain.usecase.invoice.GetInvoicesNotPaymentUseCase
import com.example.domain.usecase.invoice.PaymentInvoiceUseCase
import com.example.domain.usecase.invoice.UpdateInvoiceUseCase
import com.example.domain.usecase.product.CreateProductUseCase
import com.example.domain.usecase.product.GetProductByIdUseCase
import com.example.domain.usecase.product.GetProductsUseCase
import com.example.domain.usecase.statistic.GetStatisticByInventoryUseCase
import com.example.domain.usecase.statistic.GetStatisticByTimeUseCase
import com.example.domain.usecase.statistic.GetStatisticOverviewUseCase
import com.example.domain.usecase.synchronize.GetCountSyncUseCase
import com.example.domain.usecase.synchronize.GetLastSyncTimeUseCase
import com.example.domain.usecase.synchronize.UpdateSyncDataUseCase
import com.example.domain.usecase.unit.CreateUnitUseCase
import com.example.domain.usecase.unit.DeleteUnitUseCase
import com.example.domain.usecase.unit.GetAllUnitUseCase
import com.example.domain.usecase.unit.GetUnitDetailUseCase
import com.example.domain.usecase.unit.UpdateUnitUseCase
import org.koin.dsl.module

val domainModule = module {
    // Define the use case for inventory
    factory { CreateInventoryUseCase(get(), get()) }
    factory { DeleteInventoryUseCase(get(), get()) }
    factory { GetInventoryDetailUseCase(get()) }
    factory { GetInventoryListUseCase(get()) }
    factory { UpdateInventoryUseCase(get(), get()) }


    //Define the use case for invoice
    factory { CreateInvoiceUseCase(get(), get()) }
    factory { DeleteInvoiceUseCase(get(), get()) }
    factory { GetInventorySelectUseCase(get()) }
    factory { GetInvoiceDataToPaymentUseCase(get()) }
    factory { GetInvoiceDetailUseCase(get()) }
    factory { GetInvoicesNotPaymentUseCase(get()) }
    factory { PaymentInvoiceUseCase(get(), get()) }
    factory { UpdateInvoiceUseCase(get(), get()) }

    //Define the use case for product
    factory { CreateProductUseCase(get()) }
    factory { GetProductByIdUseCase(get()) }
    factory { GetProductsUseCase(get()) }

    //Define the use case for statistics
    factory { GetStatisticByInventoryUseCase(get()) }
    factory { GetStatisticByTimeUseCase(get()) }
    factory { GetStatisticOverviewUseCase(get()) }

    //Define the use case for synchronize
    factory { GetCountSyncUseCase(get()) }
    factory { GetLastSyncTimeUseCase(get()) }
    factory { UpdateSyncDataUseCase(get()) }

    //Define the use case for unit
    factory { CreateUnitUseCase(get(), get()) }
    factory { DeleteUnitUseCase(get(), get()) }
    factory { GetAllUnitUseCase(get()) }
    factory { GetUnitDetailUseCase(get()) }
    factory { UpdateUnitUseCase(get(), get()) }
}