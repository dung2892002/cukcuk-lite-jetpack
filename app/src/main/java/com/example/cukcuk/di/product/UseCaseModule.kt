package com.example.cukcuk.di.product

import com.example.domain.repository.ProductRepository
import com.example.domain.usecase.product.CreateProductUseCase
import com.example.domain.usecase.product.GetProductByIdUseCase
import com.example.domain.usecase.product.GetProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {
    @Provides
    fun provideCreateProductUseCase(
        repository: ProductRepository
    ): CreateProductUseCase {
        return CreateProductUseCase(repository)
    }

    @Provides
    fun provideGetProductsUseCase(
        repository: ProductRepository
    ): GetProductsUseCase {
        return GetProductsUseCase(repository)
    }

    @Provides
    fun provideGetProductByIdUseCase(
        repository: ProductRepository
    ): GetProductByIdUseCase {
        return GetProductByIdUseCase(repository)
    }
}