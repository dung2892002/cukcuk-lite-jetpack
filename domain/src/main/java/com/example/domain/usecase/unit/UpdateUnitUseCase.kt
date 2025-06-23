package com.example.domain.usecase.unit

import com.example.domain.enums.DomainError
import com.example.domain.model.ResponseData
import com.example.domain.model.Unit
import com.example.domain.repository.UnitRepository
import com.example.domain.enums.SynchronizeTable
import com.example.domain.utils.SynchronizeHelper
import java.time.LocalDateTime

class UpdateUnitUseCase (
    private val repository: UnitRepository,
    private val syncHelper: SynchronizeHelper
) {

    suspend operator fun invoke(unit: Unit) : ResponseData<Unit> {
        var response = ResponseData<Unit>(false, DomainError.UNKNOWN_ERROR)
        response.isSuccess = !repository.checkExistUnitName(unit.UnitName.trim(), unit.UnitID)
        if (!response.isSuccess) {
            response.error = DomainError.UNIT_NAME_EXIST
            response.objectData = unit
            return response
        }

        unit.ModifiedDate = LocalDateTime.now()

        response.isSuccess = repository.updateUnit(unit)

        if (response.isSuccess) {
            response.error = null
            syncHelper.updateSync(SynchronizeTable.Unit, unit.UnitID)
        }
        return response
    }
}