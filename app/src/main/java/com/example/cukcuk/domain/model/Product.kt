package com.example.cukcuk.domain.model


data class Product(
    var Id: Int = 0,
    var Title: String = "",
    var Description: String = "",
    var Price: Double = 0.0,
    var Category: String = "",
    var Image: String = ""
)