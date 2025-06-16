package com.example.domain.usecase.product

import com.example.domain.repository.ProductRepository

class CreateProductUseCase(
    private val repository: ProductRepository
) {
}