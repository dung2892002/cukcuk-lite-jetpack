package com.example.cukcuk.data.repository

import com.example.cukcuk.data.local.dao.InventoryDao
import com.example.cukcuk.domain.model.Inventory
import com.example.cukcuk.domain.repository.InventoryRepository
import java.util.UUID
import javax.inject.Inject

class InventoryRepositoryImpl @Inject constructor(
    private val dao: InventoryDao
) : InventoryRepository {
    override suspend fun getAllInventory(): List<Inventory> {
        return dao.getAllInventory()
    }

     override suspend fun getInventoryById(inventoryID: UUID): Inventory {
        return dao.getInventoryById(inventoryID)!!
    }

    override suspend fun createInventory(inventory: Inventory): Boolean {
        return dao.createInventory(inventory)
    }

    override suspend fun updateInventory(inventory: Inventory): Boolean {
        return dao.updateInventory(inventory)
    }

    override suspend fun deleteInventory(inventoryId: UUID): Boolean {
        return dao.deleteInventory(inventoryId)
    }

    override suspend fun checkInventoryIsInInvoice(inventory: Inventory): Boolean {
        return dao.checkInventoryIsInInvoice(inventory)
    }
}