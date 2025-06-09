package com.example.cukcuk.domain.model

import java.io.Serializable
import java.time.LocalDateTime
import java.util.UUID

data class Unit(
    var UnitID: UUID? = null,
    var UnitName: String = "",
    var CreatedDate: LocalDateTime? = null,
    var ModifiedDate: LocalDateTime? = null
) : Serializable
