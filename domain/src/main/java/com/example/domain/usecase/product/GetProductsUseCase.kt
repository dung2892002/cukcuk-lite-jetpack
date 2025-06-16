package com.example.domain.usecase.product

import com.example.domain.model.ApiResponse
import com.example.domain.model.Product
import com.example.domain.repository.ProductRepository

class GetProductsUseCase (
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): ApiResponse<List<Product>> {
        return repository.getAllProducts()
    }
}