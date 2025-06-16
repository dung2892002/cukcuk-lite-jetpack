package com.example.data.local.mapper

import com.example.data.local.models.UnitEntity
import com.example.domain.model.Unit

fun UnitEntity.toDomainUnit() : Unit {
    return Unit(
        UnitID = UnitID,
        UnitName = UnitName,
    )
}


fun Unit.toEntity(): UnitEntity{
    return UnitEntity(
        UnitID = UnitID,
        UnitName = UnitName,
        CreatedDate = CreatedDate,
        ModifiedDate = ModifiedDate
    )
}