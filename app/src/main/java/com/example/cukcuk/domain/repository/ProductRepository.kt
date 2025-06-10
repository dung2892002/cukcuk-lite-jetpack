package com.example.cukcuk.domain.repository

import com.example.cukcuk.domain.common.ApiResponse
import com.example.cukcuk.domain.model.Product

interface ProductRepository {
    suspend fun getAllProducts(): ApiResponse<List<Product>>
    suspend fun getProductById(id: Int): Product?
    suspend fun addProduct(product: Product): Product
    suspend fun updateProduct(product: Product): Product
    suspend fun deleteProduct(id: Int)
}