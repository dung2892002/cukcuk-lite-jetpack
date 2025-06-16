package com.example.domain.repository

import com.example.domain.model.Unit
import java.util.UUID

interface UnitRepository {
    suspend fun getAllUnit(): List<Unit>
    suspend fun createUnit(unit: Unit): Boolean
    suspend fun deleteUnit(unitId: UUID) : Boolean
    suspend fun updateUnit(unit: Unit): Boolean
    suspend fun getUnitByID(unitId: UUID): Unit
    suspend fun checkUseByInventory(unitId: UUID) : Boolean
    suspend fun checkExistUnitName(name: String, unitId: UUID?): Boolean
}