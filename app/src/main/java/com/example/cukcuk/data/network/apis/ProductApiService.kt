package com.example.cukcuk.data.network.apis

import com.example.cukcuk.data.network.models.ProductDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(): Response<List<ProductDTO>>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductDTO?

    @POST("products")
    suspend fun addProduct(@Body product: ProductDTO): ProductDTO

    @PUT("products/{id}")
    suspend fun updateProduct(@Path("id") id: Int, @Body product: ProductDTO): ProductDTO

    @DELETE("products/{id}")
    suspend fun deleteProduct(@Path("id") id: Int)
}