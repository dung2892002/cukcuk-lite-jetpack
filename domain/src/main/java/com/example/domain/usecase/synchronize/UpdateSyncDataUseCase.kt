package com.example.domain.usecase.synchronize

import com.example.domain.repository.SynchronizeRepository
import java.time.LocalDateTime

class UpdateSyncDataUseCase (
    private val repository: SynchronizeRepository
) {
    suspend operator fun invoke(time: LocalDateTime) {
        repository.updateLastSyncTime(time)
        repository.deleteAll()
    }
}