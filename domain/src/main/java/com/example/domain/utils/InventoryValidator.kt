package com.example.domain.utils

import com.example.domain.enums.DomainError
import com.example.domain.model.ResponseData
import com.example.domain.model.Inventory

object InventoryValidator {

    fun validate(inventory: Inventory): ResponseData<Inventory> {
        if (inventory.InventoryName.trim().isEmpty()) {
            return ResponseData(false, DomainError.INVENTORY_NAME_BLANK)
        }

        if (inventory.Price <= 0) {
            return ResponseData(false, DomainError.PRICE_LESS_THAN_OR_EQUAL_ZERO)
        }

        if (inventory.UnitID == null) {
            return ResponseData(false, DomainError.UNIT_NULL)
        }

        return ResponseData(true)
    }
}
