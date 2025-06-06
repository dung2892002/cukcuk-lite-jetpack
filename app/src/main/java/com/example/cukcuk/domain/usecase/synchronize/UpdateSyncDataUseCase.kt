package com.example.cukcuk.domain.usecase.synchronize

import com.example.cukcuk.domain.repository.SynchronizeRepository
import java.time.LocalDateTime
import javax.inject.Inject

class UpdateSyncDataUseCase @Inject constructor(
    private val repository: SynchronizeRepository
) {
    suspend operator fun invoke(time: LocalDateTime) {
        repository.updateLastSyncTime(time)
        repository.deleteAll()
    }
}