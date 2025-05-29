package com.example.cukcuk.domain.model

import java.io.Serializable
import java.time.LocalDateTime
import java.util.UUID

data class Unit(
    var UnitID: UUID? = null,
    var UnitName: String = "",
    var Description: String = "",
    var Inactive: Boolean = true,
    var CreatedDate: LocalDateTime? = null,
    var CreatedBy: String = "",
    var ModifiedDate: LocalDateTime? = null,
    var ModifiedBy: String = ""
) : Serializable