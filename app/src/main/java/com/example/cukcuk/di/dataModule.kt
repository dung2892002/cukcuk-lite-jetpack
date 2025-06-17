package com.example.cukcuk.di

import com.example.data.local.dao.InventoryDao
import com.example.data.local.dao.InvoiceDao
import com.example.data.local.dao.StatisticDao
import com.example.data.local.dao.SynchronizeDao
import com.example.data.local.dao.UnitDao
import com.example.data.network.apis.ProductApiService
import com.example.data.repository.InventoryRepositoryImpl
import com.example.data.repository.InvoiceRepositoryImpl
import com.example.data.repository.ProductRepositoryImpl
import com.example.data.repository.StatisticRepositoryImpl
import com.example.data.repository.SynchronizeRepositoryImpl
import com.example.data.repository.UnitRepositoryImpl
import com.example.domain.repository.InventoryRepository
import com.example.domain.repository.InvoiceRepository
import com.example.domain.repository.ProductRepository
import com.example.domain.repository.StatisticRepository
import com.example.domain.repository.SynchronizeRepository
import com.example.domain.repository.UnitRepository
import com.example.domain.utils.SynchronizeHelper
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

val dataModule = module {
    single { InventoryDao(get()) }
    single<InventoryRepository> { InventoryRepositoryImpl(get()) }

    single { InvoiceDao(get()) }
    single<InvoiceRepository> { InvoiceRepositoryImpl(get()) }

    single { StatisticDao(get()) }
    single<StatisticRepository> { StatisticRepositoryImpl(get()) }

    single { SynchronizeDao(get()) }
    single<SynchronizeRepository> { SynchronizeRepositoryImpl(get()) }
    single { SynchronizeHelper(get()) }

    single { UnitDao(get()) }
    single<UnitRepository> { UnitRepositoryImpl(get()) }

    single {
        Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single<ProductApiService> { get<Retrofit>().create(ProductApiService::class.java) }
    single<ProductRepository> { ProductRepositoryImpl(get()) }
}