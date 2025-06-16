package com.example.domain.usecase.unit

import com.example.domain.model.Unit
import com.example.domain.repository.UnitRepository
import java.util.UUID

class GetUnitDetailUseCase (
    private val repository: UnitRepository
) {
    suspend operator fun invoke(unitId: UUID): Unit {
        return repository.getUnitByID(unitId)
    }

}