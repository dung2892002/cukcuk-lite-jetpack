package com.example.data.network.models

data class ProductDTO (
    var id: Int = 0,
    var title: String = "",
    var description: String = "",
    var price: Double = 0.0,
    var category: String = "",
    var image: String = ""
)