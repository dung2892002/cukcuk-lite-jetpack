package com.example.cukcuk.domain.usecase.unit

import com.example.cukcuk.domain.model.Unit
import com.example.cukcuk.domain.repository.UnitRepository
import java.util.UUID
import javax.inject.Inject

class GetUnitDetailUseCase @Inject constructor(
    private val repository: UnitRepository
) {
    operator fun invoke(unitId: UUID): Unit {
        return repository.getUnitByID(unitId)
    }

}