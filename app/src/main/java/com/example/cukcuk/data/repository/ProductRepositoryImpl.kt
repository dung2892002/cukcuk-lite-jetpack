package com.example.cukcuk.data.repository

import com.example.cukcuk.data.network.apis.ProductApiService
import com.example.cukcuk.domain.model.Product
import com.example.cukcuk.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApiService
) : ProductRepository {
    override suspend fun getAllProducts(): List<Product> {
        return productApi.getProducts()
    }

    override suspend fun getProductById(id: Int): Product? {
        return productApi.getProductById(id)
    }

    override suspend fun addProduct(product: Product): Product {
        return productApi.addProduct(product)
    }

    override suspend fun updateProduct(product: Product): Product {
        return productApi.updateProduct(product.id, product)
    }

    override suspend fun deleteProduct(id: Int) {
        return productApi.deleteProduct(id)
    }
}