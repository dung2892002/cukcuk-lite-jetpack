package com.example.domain.usecase.inventory

import com.example.domain.model.ResponseData
import com.example.domain.model.Inventory
import com.example.domain.repository.InventoryRepository
import com.example.domain.utils.InventoryValidator
import com.example.domain.enums.SynchronizeTable
import com.example.domain.utils.SynchronizeHelper
import java.time.LocalDateTime

class UpdateInventoryUseCase (
    private val repository: InventoryRepository,
    private val syncHelper: SynchronizeHelper
){
    suspend operator fun invoke(inventory: Inventory) : ResponseData<Inventory> {
        val response = InventoryValidator.validate(inventory)
        if (!response.isSuccess) {
            return response
        }

        inventory.ModifiedDate = LocalDateTime.now()

        response.isSuccess = repository.updateInventory(inventory)
        if (response.isSuccess) {
            response.error = null
            syncHelper.updateSync(SynchronizeTable.Inventory, inventory.InventoryID)
        }
        return response
    }
}