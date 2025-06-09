package com.example.cukcuk.domain.utils

import com.example.cukcuk.domain.model.ResponseData
import com.example.cukcuk.domain.model.Inventory

object InventoryValidator {
    fun validate(inventory: Inventory): ResponseData {
        if (inventory.InventoryName.isEmpty()) {
            return ResponseData(false, "Tên món ăn không được để trống")
        }

        if (inventory.Price <= 0) {
            return ResponseData(false, "Giá phải lớn hơn 0")
        }

        if (inventory.UnitID == null) {
            return ResponseData(false, "Đơn vị tính không được để trống")
        }

        return ResponseData(true)
    }
}
