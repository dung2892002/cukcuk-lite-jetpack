package com.example.domain.usecase.inventory

import com.example.domain.enums.DomainError
import com.example.domain.enums.SynchronizeTable
import com.example.domain.model.Inventory
import com.example.domain.model.ResponseData
import com.example.domain.repository.InventoryRepository
import com.example.domain.utils.SynchronizeHelper

class DeleteInventoryUseCase(
    private val repository: InventoryRepository,
    private val syncHelper: SynchronizeHelper
) {
    suspend operator fun invoke(inventory: Inventory) : ResponseData<Inventory> {
         val response = ResponseData<Inventory>(false, DomainError.UNKNOWN_ERROR, inventory)

         response.isSuccess = !repository.checkInventoryIsInInvoice(inventory)
         if (!response.isSuccess) {
             response.error = DomainError.INVENTORY_IS_USED_IN_INVOICE
             return response
         }
         response.isSuccess = repository.deleteInventory(inventory.InventoryID!!)
         if (response.isSuccess) {
             response.error = null
             syncHelper.deleteSync(SynchronizeTable.Inventory, inventory.InventoryID)
         }
         return response
    }

}