package com.example.cukcuk.data.mapper

import com.example.cukcuk.data.local.entities.InventoryEntity
import com.example.cukcuk.domain.model.Inventory

fun Inventory.toEntity() : InventoryEntity {
    return InventoryEntity(
        InventoryID = InventoryID,
        InventoryName = InventoryName,
        Price = Price,
        Inactive = Inactive,
        CreatedDate = CreatedDate,
        ModifiedDate = ModifiedDate,
        Color = Color,
        IconFileName = IconFileName,
        UnitID = UnitID,
        UnitName = UnitName
    )
}


fun InventoryEntity.toDomainInventory() : Inventory {
    return Inventory(
        InventoryID = InventoryID,
        InventoryName = InventoryName,
        Price = Price,
        Inactive = Inactive,
        CreatedDate = CreatedDate,
        ModifiedDate = ModifiedDate,
        Color = Color,
        IconFileName = IconFileName,
        UnitID = UnitID,
        UnitName = UnitName
    )
}