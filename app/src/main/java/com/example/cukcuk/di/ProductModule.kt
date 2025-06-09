package com.example.cukcuk.di

import com.example.cukcuk.data.network.apis.ProductApiService
import com.example.cukcuk.data.repository.ProductRepositoryImpl
import com.example.cukcuk.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProductModule {

    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit): ProductApiService {
        return retrofit.create(ProductApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideProductRepository(productApi: ProductApiService): ProductRepository {
        return ProductRepositoryImpl(productApi)
    }
}
