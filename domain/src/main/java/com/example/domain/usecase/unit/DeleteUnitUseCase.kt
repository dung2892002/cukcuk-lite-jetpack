package com.example.domain.usecase.unit

import com.example.domain.enums.DomainError
import com.example.domain.model.ResponseData
import com.example.domain.model.Unit
import com.example.domain.repository.UnitRepository
import com.example.domain.enums.SynchronizeTable
import com.example.domain.utils.SynchronizeHelper

class DeleteUnitUseCase(
    private val repository: UnitRepository,
    private val syncHelper: SynchronizeHelper
){
    suspend operator fun invoke(unit: Unit) : ResponseData<Unit> {
        var response = ResponseData<Unit>(false, DomainError.UNKNOWN_ERROR)

        response.isSuccess = !repository.checkUseByInventory(unit.UnitID!!)
        if (!response.isSuccess) {
            response.error = DomainError.UNIT_IS_USED
            response.objectData = unit
            return response
        }

        response.isSuccess = repository.deleteUnit(unit.UnitID!!)
        if (response.isSuccess) {
            response.error = null
            syncHelper.deleteSync(SynchronizeTable.Unit, unit.UnitID)
        }
        return response
    }
}