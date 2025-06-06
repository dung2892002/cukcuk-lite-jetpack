package com.example.cukcuk.domain.usecase.inventory

import com.example.cukcuk.domain.dtos.ResponseData
import com.example.cukcuk.domain.model.Inventory
import com.example.cukcuk.domain.repository.InventoryRepository
import com.example.cukcuk.utils.SynchronizeHelper
import javax.inject.Inject

class UpdateInventoryUseCase @Inject constructor(
    private val repository: InventoryRepository,
    private val syncHelper: SynchronizeHelper
){
    suspend operator fun invoke(inventory: Inventory) : ResponseData {
        val response = ResponseData(false, "Có lỗi xảy ra")
        response.isSuccess = repository.updateInventory(inventory)
        if (response.isSuccess) {
            response.message = null
            syncHelper.updateSync("Inventory", inventory.InventoryID)
        }
        return response
    }
}