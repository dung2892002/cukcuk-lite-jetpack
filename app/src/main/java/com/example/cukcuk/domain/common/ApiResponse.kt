package com.example.cukcuk.domain.common

data class ApiResponse<T> (
    val isSuccess: Boolean,
    val message: String? = null,
    val data: T? = null
)