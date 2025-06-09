package com.example.cukcuk.domain.usecase.product

import com.example.cukcuk.domain.model.Product
import com.example.cukcuk.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): List<Product> {
        return repository.getAllProducts()
    }
}