package com.example.cukcuk.domain.usecase.unit

import com.example.cukcuk.domain.model.Unit
import com.example.cukcuk.domain.repository.UnitRepository
import javax.inject.Inject

class GetAllUnitUseCase @Inject constructor(
    private val repository: UnitRepository
) {
    suspend operator fun invoke(): List<Unit> {
        return repository.getAllUnit()
    }
}