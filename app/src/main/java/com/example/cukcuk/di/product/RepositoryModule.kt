package com.example.cukcuk.di.product

import com.example.data.network.apis.ProductApiService
import com.example.data.repository.ProductRepositoryImpl
import com.example.domain.repository.ProductRepository
import com.example.domain.usecase.product.CreateProductUseCase
import com.example.domain.usecase.product.GetProductByIdUseCase
import com.example.domain.usecase.product.GetProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideProductApi(retrofit: Retrofit): ProductApiService {
        return retrofit.create(ProductApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepositoryImpl(
        productApi: ProductApiService
    ) : ProductRepositoryImpl {
        return ProductRepositoryImpl(productApi)
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        repository: ProductRepositoryImpl
    ): ProductRepository {
        return repository
    }
}