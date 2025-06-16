package com.example.domain.model

data class InventorySelect(
    val inventory: Inventory = Inventory(),
    val quantity: Double = 0.0
)