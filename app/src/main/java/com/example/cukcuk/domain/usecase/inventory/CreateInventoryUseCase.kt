package com.example.cukcuk.domain.usecase.inventory

import com.example.cukcuk.domain.model.Inventory
import com.example.cukcuk.domain.repository.InventoryRepository
import javax.inject.Inject

class CreateInventoryUseCase @Inject constructor(
    private val repository: InventoryRepository
) {
    suspend operator fun invoke(inventory: Inventory) {
        repository.createInventory(inventory)
    }

}