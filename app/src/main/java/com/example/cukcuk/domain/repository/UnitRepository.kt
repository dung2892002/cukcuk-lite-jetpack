package com.example.cukcuk.domain.repository

import com.example.cukcuk.domain.model.Unit
import java.util.UUID

interface UnitRepository {
    fun getAllUnit(): List<Unit>
    fun createUnit(unit: Unit): Boolean
    fun deleteUnit(unitId: UUID) : Boolean
    fun updateUnit(unit: Unit): Boolean
    fun getUnitByID(unitId: UUID): Unit
    fun checkUseByInventory(unitId: UUID) : Boolean
    fun checkExistUnitName(name: String, unitId: UUID?): Boolean
}