package com.example.cukcuk.data.repository

import com.example.cukcuk.data.local.dao.InventoryDao
import com.example.cukcuk.domain.model.Inventory
import com.example.cukcuk.domain.repository.InventoryRepository
import java.util.UUID
import javax.inject.Inject

class InventoryRepositoryImpl @Inject constructor(
    private val dao: InventoryDao
) : InventoryRepository {
    override fun getAllInventory(): List<Inventory> {
        return dao.getAllInventory()
    }

    override fun getInventoryById(inventoryID: UUID): Inventory {
        return dao.getInventoryById(inventoryID)!!
    }

    override fun createInventory(inventory: Inventory): Boolean {
        return dao.createInventory(inventory)
    }

    override fun updateInventory(inventory: Inventory): Boolean {
        return dao.updateInventory(inventory)
    }

    override fun deleteInventory(inventoryId: UUID): Boolean {
        return dao.deleteInventory(inventoryId)
    }

    override fun checkInventoryIsInInvoice(inventory: Inventory): Boolean {
        return dao.checkInventoryIsInInvoice(inventory)
    }
}