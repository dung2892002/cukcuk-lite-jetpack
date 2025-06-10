package com.example.cukcuk.domain.usecase.product

import com.example.cukcuk.domain.repository.ProductRepository
import javax.inject.Inject

class CreateProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
}