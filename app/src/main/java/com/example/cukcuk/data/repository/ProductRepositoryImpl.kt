package com.example.cukcuk.data.repository

import com.example.cukcuk.data.network.apis.ProductApiService
import com.example.cukcuk.data.network.mapper.toDTO
import com.example.cukcuk.data.network.mapper.toDomainModel
import com.example.cukcuk.domain.common.ApiResponse
import com.example.cukcuk.domain.model.Product
import com.example.cukcuk.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productApi: ProductApiService
) : ProductRepository {
    override suspend fun getAllProducts(): ApiResponse<List<Product>> {
        try {
            val response = productApi.getProducts()
            if (response.isSuccessful) {
                val products = response.body()?.map { it.toDomainModel() } ?: emptyList()
                return ApiResponse(
                    isSuccess = true,
                    message = null,
                    data = products
                )
            } else {
                return ApiResponse(
                    isSuccess = false,
                    message = response.message(),
                    data = null
                )
            }
        } catch (e: Exception) {
            return ApiResponse(
                isSuccess = false,
                message = e.localizedMessage ?: "Unknown error",
                data = null
            )
        }
    }

    override suspend fun getProductById(id: Int): Product? {
        val product = productApi.getProductById(id)
        return product?.toDomainModel()
    }

    override suspend fun addProduct(product: Product): Product {
        return try {
            val result = productApi.addProduct(product.toDTO())
            result.toDomainModel()
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw ex
        }
    }

    override suspend fun updateProduct(product: Product): Product {
        return try {
            val result = productApi.updateProduct(product.Id, product.toDTO())
            result.toDomainModel()
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw ex
        }
    }

    override suspend fun deleteProduct(id: Int) {
        return productApi.deleteProduct(id)
    }
}