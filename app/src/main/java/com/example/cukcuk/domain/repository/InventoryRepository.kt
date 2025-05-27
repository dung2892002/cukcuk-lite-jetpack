package com.example.cukcuk.domain.repository

import com.example.cukcuk.domain.model.Inventory
import java.util.UUID

interface InventoryRepository {
    fun getAllInventory() : List<Inventory>
    fun getInventoryById(inventoryID: UUID) : Inventory
    fun createInventory(inventory: Inventory): Boolean
    fun updateInventory(inventory: Inventory): Boolean
    fun deleteInventory(inventoryId: UUID): Boolean
    fun checkInventoryIsInInvoice(inventory: Inventory): Boolean
}