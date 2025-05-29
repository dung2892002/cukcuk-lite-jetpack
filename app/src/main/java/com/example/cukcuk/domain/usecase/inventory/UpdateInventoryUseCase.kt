package com.example.cukcuk.domain.usecase.inventory

import com.example.cukcuk.domain.model.Inventory
import com.example.cukcuk.domain.repository.InventoryRepository
import javax.inject.Inject

class UpdateInventoryUseCase @Inject constructor(
    private val repository: InventoryRepository
){
    operator fun invoke(inventory: Inventory) : Boolean {
        return repository.updateInventory(inventory)
    }
}