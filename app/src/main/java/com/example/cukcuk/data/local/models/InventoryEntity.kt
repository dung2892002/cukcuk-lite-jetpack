package com.example.cukcuk.data.local.models

import java.time.LocalDateTime
import java.util.UUID

data class InventoryEntity(
    var InventoryID: UUID? = null,
    var InventoryCode: String = "",
    var InventoryName: String = "",
    var InventoryType: Int = 0,
    var Price: Double = 0.0,
    var Description: String = "",
    var Inactive: Boolean = true,
    var CreatedDate: LocalDateTime? = null,
    var CreatedBy: String = "",
    var ModifiedDate: LocalDateTime? = null,
    var ModifiedBy: String = "",
    var Color: String = "#00FF00",
    var IconFileName: String = "ic_default.png",
    var UseCount: Int = 0,
    var UnitID: UUID? = null,
    var UnitName: String = ""
)