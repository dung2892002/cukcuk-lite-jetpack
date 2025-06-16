package com.example.domain.model

import java.time.LocalDateTime
import java.util.UUID


data class Inventory(
    var InventoryID: UUID? = null,
    var InventoryName: String = "",
    var Price: Double = 0.0,
    var Inactive: Boolean = true,
    var CreatedDate: LocalDateTime? = null,
    var ModifiedDate: LocalDateTime? = null,
    var Color: String = "#00FF00",
    var IconFileName: String = "ic_default.png",
    var UnitID: UUID? = null,
    var UnitName: String = ""
)