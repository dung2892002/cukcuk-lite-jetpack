package com.example.cukcuk.data.mapper

import com.example.cukcuk.data.local.entities.UnitEntity
import com.example.cukcuk.domain.model.Unit

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