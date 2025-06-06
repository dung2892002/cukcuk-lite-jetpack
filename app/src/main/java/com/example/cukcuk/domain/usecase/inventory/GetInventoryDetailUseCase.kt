package com.example.cukcuk.domain.usecase.inventory

import com.example.cukcuk.domain.model.Inventory
import com.example.cukcuk.domain.repository.InventoryRepository
import java.util.UUID
import javax.inject.Inject

class GetInventoryDetailUseCase @Inject constructor(
    private val repository: InventoryRepository
){
    suspend operator fun invoke(inventoryId: UUID): Inventory {
        return repository.getInventoryById(inventoryId)
    }

}