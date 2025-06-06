package com.example.cukcuk.data.repository

import com.example.cukcuk.data.local.dao.UnitDao
import com.example.cukcuk.domain.model.Unit
import com.example.cukcuk.domain.repository.UnitRepository
import java.util.UUID
import javax.inject.Inject

class UnitRepositoryImpl @Inject constructor(
    private val dao: UnitDao
) : UnitRepository {
    override suspend fun getAllUnit(): List<Unit> {
        return dao.getAllUnit()
    }

    override suspend fun createUnit(unit: Unit) : Boolean {
        return dao.createUnit(unit)
    }

    override suspend fun deleteUnit(unitId: UUID) : Boolean {
        return dao.deleteUnit(unitId)
    }

    override suspend fun updateUnit(unit: Unit) : Boolean {
        return dao.updateUnit(unit)
    }

    override suspend fun getUnitByID(unitId: UUID): Unit {
        return dao.getUnitById(unitId)!!
    }

    override suspend fun checkUseByInventory(unitId: UUID): Boolean {
        return dao.checkUseByInventory(unitId)
    }

    override suspend fun checkExistUnitName(name: String, unitId: UUID?): Boolean {
        return dao.checkExistUnitName(name, unitId)
    }
}
