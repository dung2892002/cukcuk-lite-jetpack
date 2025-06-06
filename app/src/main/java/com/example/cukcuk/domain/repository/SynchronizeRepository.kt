package com.example.cukcuk.domain.repository

import com.example.cukcuk.domain.model.SynchronizeData
import java.time.LocalDateTime
import java.util.UUID

interface SynchronizeRepository {
    suspend fun deleteAll()
    suspend fun countSync(): Int
    suspend fun getLastSyncTime() : LocalDateTime?
    suspend fun updateLastSyncTime(lastSyncTime: LocalDateTime)
    suspend fun getAllSync() : MutableList<SynchronizeData>
    suspend fun create(tableName: String, objectId: UUID, action: Int)
    suspend fun delete(syncId: UUID)
    suspend fun createRange(tableName: String, objectIds: List<UUID>, action: Int)
    suspend fun deleteRange(syncIds: List<UUID>)
    suspend fun deleteDataBeforeCreateDeleteSync(tableName: String, objectId: UUID)
    suspend fun getExistingSyncIdForCreateNew(tableName: String, objectId: UUID): UUID?
    suspend fun getExistingSyncIdForCreateNewOrUpdate(tableName: String, objectId: UUID): UUID?
    suspend fun getExistingSyncIdsForCreateNewOrUpdate(tableName: String, objectIds: List<UUID>): Set<UUID>
}