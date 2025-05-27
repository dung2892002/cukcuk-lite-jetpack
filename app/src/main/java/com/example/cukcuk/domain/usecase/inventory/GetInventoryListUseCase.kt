package com.example.cukcuk.domain.usecase.inventory

import com.example.cukcuk.domain.model.Inventory
import com.example.cukcuk.domain.repository.InventoryRepository
import javax.inject.Inject

class GetInventoryListUseCase @Inject constructor(
    private val inventoryRepository: InventoryRepository
) {
    operator fun invoke(): List<Inventory> {
        return inventoryRepository.getAllInventory()
    }

}