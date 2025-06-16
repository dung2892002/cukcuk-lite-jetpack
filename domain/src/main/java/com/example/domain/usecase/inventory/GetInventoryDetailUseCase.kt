package com.example.domain.usecase.inventory

import com.example.domain.model.Inventory
import com.example.domain.repository.InventoryRepository
import java.util.UUID

class GetInventoryDetailUseCase (
    private val repository: InventoryRepository
){
    suspend operator fun invoke(inventoryId: UUID): Inventory {
        return repository.getInventoryById(inventoryId)
    }

}