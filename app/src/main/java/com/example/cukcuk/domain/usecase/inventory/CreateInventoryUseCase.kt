package com.example.cukcuk.domain.usecase.inventory

import com.example.cukcuk.domain.dtos.ResponseData
import com.example.cukcuk.domain.model.Inventory
import com.example.cukcuk.domain.repository.InventoryRepository
import javax.inject.Inject

class CreateInventoryUseCase @Inject constructor(
    private val repository: InventoryRepository
) {
    operator fun invoke(inventory: Inventory) : ResponseData {
        val response = ResponseData(false, "Có lỗi xảy ra")
        response.isSuccess = repository.createInventory(inventory)
        if (response.isSuccess) response.message = null
        return response
    }

}