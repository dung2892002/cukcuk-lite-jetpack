package com.example.cukcuk.domain.repository

import com.example.cukcuk.domain.model.Inventory
import java.util.UUID

interface InventoryRepository {
    suspend fun getAllInventory() : List<Inventory>
    suspend fun getInventoryById(inventoryID: UUID) : Inventory
    suspend fun createInventory(inventory: Inventory): Boolean
    suspend fun updateInventory(inventory: Inventory): Boolean
    suspend fun deleteInventory(inventoryId: UUID): Boolean
    suspend fun checkInventoryIsInInvoice(inventory: Inventory): Boolean
}