package com.example.cukcuk.domain.usecase.synchronize

import com.example.cukcuk.domain.repository.SynchronizeRepository
import java.time.LocalDateTime
import javax.inject.Inject

class GetLastSyncTimeUseCase @Inject constructor(
    private val repository: SynchronizeRepository
) {
    suspend operator fun invoke(): LocalDateTime? {
        return repository.getLastSyncTime()
    }
}