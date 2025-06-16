package com.example.domain.usecase.inventory

import com.example.domain.model.Inventory
import com.example.domain.repository.InventoryRepository

class GetInventoryListUseCase (
    private val inventoryRepository: InventoryRepository
) {
    suspend operator fun invoke(): List<Inventory> {
        return inventoryRepository.getAllInventory()
    }

}