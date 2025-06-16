package com.example.domain.usecase.synchronize

import com.example.domain.repository.SynchronizeRepository
import java.time.LocalDateTime

class GetLastSyncTimeUseCase (
    private val repository: SynchronizeRepository
) {
    suspend operator fun invoke(): LocalDateTime? {
        return repository.getLastSyncTime()
    }
}