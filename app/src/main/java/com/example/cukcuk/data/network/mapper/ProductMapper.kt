package com.example.cukcuk.data.network.mapper

import com.example.cukcuk.data.network.models.ProductDTO
import com.example.cukcuk.domain.model.Product

fun ProductDTO.toDomainModel() = Product(
    Id = this.id,
    Title = this.title,
    Description = this.description,
    Price = this.price,
    Category = this.category,
    Image = this.image
)

fun Product.toDTO() = ProductDTO(
    id = this.Id,
    title = this.Title,
    description = this.Description,
    price = this.Price,
    category = this.Category,
    image = this.Image
)