package com.example.domain.usecase.synchronize

import com.example.domain.repository.SynchronizeRepository

class GetCountSyncUseCase (
    private val repository: SynchronizeRepository
) {
    suspend operator fun invoke(): Int {
        return repository.countSync()
    }
}