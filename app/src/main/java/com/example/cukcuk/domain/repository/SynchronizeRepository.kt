package com.example.cukcuk.domain.repository

import com.example.cukcuk.domain.model.SynchronizeData
import java.time.LocalDateTime
import java.util.UUID

interface SynchronizeRepository {
    fun deleteAll()
    fun countSync(): Int
    fun getLastSyncTime() : LocalDateTime?
    fun updateLastSyncTime(lastSyncTime: LocalDateTime)
    fun getAllSync() : MutableList<SynchronizeData>
    fun create(tableName: String, objectId: UUID, action: Int)
    fun delete(syncId: UUID)
    fun createRange(tableName: String, objectIds: List<UUID>, action: Int)
    fun deleteRange(syncIds: List<UUID>)
    fun deleteDataBeforeCreateDeleteSync(tableName: String, objectId: UUID)
    fun getExistingSyncIdForCreateNew(tableName: String, objectId: UUID): UUID?
    fun getExistingSyncIdForCreateNewOrUpdate(tableName: String, objectId: UUID): UUID?
    fun getExistingSyncIdsForCreateNewOrUpdate(tableName: String, objectIds: List<UUID>): Set<UUID>
}