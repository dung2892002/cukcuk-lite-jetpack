package com.example.domain.usecase.unit

import com.example.domain.model.Unit
import com.example.domain.repository.UnitRepository

class GetAllUnitUseCase (
    private val repository: UnitRepository
) {
    suspend operator fun invoke(): List<Unit> {
        return repository.getAllUnit()
    }
}