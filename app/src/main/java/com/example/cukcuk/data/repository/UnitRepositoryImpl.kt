package com.example.cukcuk.data.repository

import com.example.cukcuk.data.local.dao.UnitDao
import com.example.cukcuk.data.local.mapper.toDomainUnit
import com.example.cukcuk.data.local.mapper.toEntity
import com.example.cukcuk.domain.model.Unit
import com.example.cukcuk.domain.repository.UnitRepository
import java.util.UUID
import javax.inject.Inject

class UnitRepositoryImpl @Inject constructor(
    private val dao: UnitDao
) : UnitRepository {
    override suspend fun getAllUnit(): List<Unit> {
        val unitsLocal = dao.getAllUnit()
        return unitsLocal.map { it.toDomainUnit() }
    }

    override suspend fun createUnit(unit: Unit) : Boolean {
        val unitEntity = unit.toEntity()
        return dao.createUnit(unitEntity)
    }

    override suspend fun deleteUnit(unitId: UUID) : Boolean {
        return dao.deleteUnit(unitId)
    }

    override suspend fun updateUnit(unit: Unit) : Boolean {
        val unitEntity = unit.toEntity()
        return dao.updateUnit(unitEntity)
    }

    override suspend fun getUnitByID(unitId: UUID): Unit {
        val unitEntity = dao.getUnitById(unitId)!!
        return unitEntity.toDomainUnit()
    }

    override suspend fun checkUseByInventory(unitId: UUID): Boolean {
        return dao.checkUseByInventory(unitId)
    }

    override suspend fun checkExistUnitName(name: String, unitId: UUID?): Boolean {
        return dao.checkExistUnitName(name, unitId)
    }
}
