package com.example.cukcuk.domain.usecase.product

import com.example.cukcuk.domain.model.Product
import com.example.cukcuk.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(id: Int): Product? {
        val product = repository.getProductById(id)

        return product
    }
}