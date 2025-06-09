package com.example.cukcuk.data.local.entities

import java.time.LocalDateTime
import java.util.UUID

data class UnitEntity(
    val UnitID: UUID? = null,
    val UnitName: String,
    val Description: String = "",
    val Inactive: Boolean = true,
    var CreatedDate: LocalDateTime? = null,
    var CreatedBy: String = "",
    var ModifiedDate: LocalDateTime? = null,
    var ModifiedBy: String = ""
)


