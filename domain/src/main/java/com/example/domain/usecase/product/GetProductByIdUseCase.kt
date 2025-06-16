package com.example.domain.usecase.product

import com.example.domain.model.Product
import com.example.domain.repository.ProductRepository

class GetProductByIdUseCase (
    private val repository: ProductRepository
) {
    suspend operator fun invoke(id: Int): Product? {
        val product = repository.getProductById(id)

        return product
    }
}