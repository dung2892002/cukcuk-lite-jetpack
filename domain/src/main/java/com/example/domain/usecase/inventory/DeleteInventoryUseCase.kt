package com.example.domain.usecase.inventory

import com.example.domain.enums.SynchronizeTable
import com.example.domain.model.Inventory
import com.example.domain.model.ResponseData
import com.example.domain.repository.InventoryRepository
import com.example.domain.utils.SynchronizeHelper

class DeleteInventoryUseCase(
    private val repository: InventoryRepository,
    private val syncHelper: SynchronizeHelper
) {
    suspend operator fun invoke(inventory: Inventory) : ResponseData {
         val response = ResponseData(false, "Có lỗi xảy ra!")

         response.isSuccess = !repository.checkInventoryIsInInvoice(inventory)
         if (!response.isSuccess) {
             response.message = "Món ${inventory.InventoryName} đã được sử dụng. Bạn không được phép xóa"
             return response
         }
         response.isSuccess = repository.deleteInventory(inventory.InventoryID!!)
         if (response.isSuccess) {
             response.message = null
             syncHelper.deleteSync(SynchronizeTable.Inventory, inventory.InventoryID)
         }
         return response
    }

}