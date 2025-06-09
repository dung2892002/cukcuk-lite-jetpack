package com.example.cukcuk.domain.usecase.inventory

import com.example.cukcuk.domain.dtos.ResponseData
import com.example.cukcuk.domain.model.Inventory
import com.example.cukcuk.domain.repository.InventoryRepository
import com.example.cukcuk.domain.utils.InventoryValidator
import com.example.cukcuk.presentation.enums.SynchronizeTable
import com.example.cukcuk.utils.SynchronizeHelper
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class CreateInventoryUseCase @Inject constructor(
    private val repository: InventoryRepository,
    private val syncHelper: SynchronizeHelper
) {
    suspend operator fun invoke(inventory: Inventory) : ResponseData {
        val response = InventoryValidator.validate(inventory)
        if (!response.isSuccess) {
            return response
        }

        inventory.InventoryID = UUID.randomUUID()
        inventory.CreatedDate = LocalDateTime.now()

        response.isSuccess = repository.createInventory(inventory)
        if (response.isSuccess) {
            response.message = null
            syncHelper.insertSync(SynchronizeTable.Inventory, inventory.InventoryID)
        }
        return response
    }

}