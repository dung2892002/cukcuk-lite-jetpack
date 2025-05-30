package com.example.cukcuk.domain.usecase.inventory

import com.example.cukcuk.domain.dtos.ResponseData
import com.example.cukcuk.domain.model.Inventory
import com.example.cukcuk.domain.repository.InventoryRepository
import javax.inject.Inject

class DeleteInventoryUseCase @Inject constructor(
    private val repository: InventoryRepository
) {
     operator fun invoke(inventory: Inventory) : ResponseData {
         val response = ResponseData(false, "Có lỗi xảy ra!")

         response.isSuccess = !repository.checkInventoryIsInInvoice(inventory)
         if (!response.isSuccess) {
             response.message = "Món ${inventory.InventoryName} đã được sử dụng. Bạn không được phép xóa"
             return response
         }
         response.isSuccess = repository.deleteInventory(inventory.InventoryID!!)
         if (response.isSuccess) response.message = null
         return response
    }

}