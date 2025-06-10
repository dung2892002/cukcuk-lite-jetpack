package com.example.cukcuk.domain.usecase.inventory

import com.example.cukcuk.domain.common.ResponseData
import com.example.cukcuk.domain.model.Inventory
import com.example.cukcuk.domain.repository.InventoryRepository
import com.example.cukcuk.domain.utils.InventoryValidator
import com.example.cukcuk.presentation.enums.SynchronizeTable
import com.example.cukcuk.utils.SynchronizeHelper
import java.time.LocalDateTime
import javax.inject.Inject

class UpdateInventoryUseCase @Inject constructor(
    private val repository: InventoryRepository,
    private val syncHelper: SynchronizeHelper
){
    suspend operator fun invoke(inventory: Inventory) : ResponseData {
        val response = InventoryValidator.validate(inventory)
        if (!response.isSuccess) {
            return response
        }

        inventory.ModifiedDate = LocalDateTime.now()

        response.isSuccess = repository.updateInventory(inventory)
        if (response.isSuccess) {
            response.message = null
            syncHelper.updateSync(SynchronizeTable.Inventory, inventory.InventoryID)
        }
        return response
    }
}