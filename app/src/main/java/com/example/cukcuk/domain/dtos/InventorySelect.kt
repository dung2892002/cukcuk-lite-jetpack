package com.example.cukcuk.domain.dtos


import com.example.cukcuk.domain.model.Inventory
import java.io.Serializable

data class InventorySelect(
    val inventory: Inventory = Inventory(),
    var quantity: Double = 0.0
) : Serializable