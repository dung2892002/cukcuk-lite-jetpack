package com.example.domain.repository

import com.example.domain.model.ApiResponse
import com.example.domain.model.Product

interface ProductRepository {
    suspend fun getAllProducts(): ApiResponse<List<Product>>
    suspend fun getProductById(id: Int): Product?
    suspend fun addProduct(product: Product): Product
    suspend fun updateProduct(product: Product): Product
    suspend fun deleteProduct(id: Int)
}