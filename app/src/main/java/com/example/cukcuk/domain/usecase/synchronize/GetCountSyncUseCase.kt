package com.example.cukcuk.domain.usecase.synchronize

import com.example.cukcuk.domain.repository.SynchronizeRepository
import javax.inject.Inject

class GetCountSyncUseCase @Inject constructor(
    private val repository: SynchronizeRepository
) {
    suspend operator fun invoke(): Int {
        return repository.countSync()
    }
}