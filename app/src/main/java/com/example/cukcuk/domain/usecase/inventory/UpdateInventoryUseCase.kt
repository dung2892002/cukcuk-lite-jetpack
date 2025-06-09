package com.example.cukcuk.domain.usecase.inventory

import com.example.cukcuk.domain.dtos.ResponseData
import com.example.cukcuk.domain.model.Inventory
import com.example.cukcuk.domain.repository.InventoryRepository
import com.example.cukcuk.utils.SynchronizeHelper
import java.time.LocalDateTime
import javax.inject.Inject

class UpdateInventoryUseCase @Inject constructor(
    private val repository: InventoryRepository,
    private val syncHelper: SynchronizeHelper
){
    suspend operator fun invoke(inventory: Inventory) : ResponseData {
        val response = ResponseData(false, "Có lỗi xảy ra")

        if (inventory.InventoryName.isEmpty()) {
            response.message = "Tên món ăn không được để trống"
            return response
        }

        if (inventory.Price <= 0) {
            response.message = "Giá phải lớn hơn 0"
            return response
        }

        if (inventory.UnitID == null) {
            response.message = "Đơn vị tính không được để trống"
            return response
        }

        inventory.ModifiedDate = LocalDateTime.now()

        response.isSuccess = repository.updateInventory(inventory)
        if (response.isSuccess) {
            response.message = null
            syncHelper.updateSync("Inventory", inventory.InventoryID)
        }
        return response
    }
}