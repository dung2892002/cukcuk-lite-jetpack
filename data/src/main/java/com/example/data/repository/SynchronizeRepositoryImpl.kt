package com.example.data.repository

import com.example.data.local.dao.SynchronizeDao
import com.example.domain.model.SynchronizeData
import com.example.domain.repository.SynchronizeRepository
import java.time.LocalDateTime
import java.util.UUID

class SynchronizeRepositoryImpl (
    private val dao: SynchronizeDao
) : SynchronizeRepository {

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

    override suspend fun countSync(): Int {
        return dao.countSync()
    }

    override suspend fun getLastSyncTime(): LocalDateTime? {
        return dao.getLastSyncTime()
    }

    override suspend fun updateLastSyncTime(lastSyncTime: LocalDateTime) {
        dao.updateLastSyncTime(lastSyncTime)
    }

    override suspend fun getAllSync(): MutableList<SynchronizeData> {
        return dao.getAllSync()
    }

    override suspend fun create(tableName: String, objectId: UUID, action: Int) {
        dao.create(tableName, objectId, action)
    }

    override suspend fun delete(syncId: UUID) {
        dao.delete(syncId)
    }

    override suspend fun createRange(
        tableName: String,
        objectIds: List<UUID>,
        action: Int
    ) {
        dao.createRange(tableName, objectIds, action)
    }

    override suspend fun deleteRange(syncIds: List<UUID>) {
        dao.deleteRange(syncIds)
    }

    override suspend fun deleteDataBeforeCreateDeleteSync(
        tableName: String,
        objectId: UUID
    ) {
        dao.deleteDataBeforeCreateDeleteSync(tableName, objectId)
    }

    override suspend fun getExistingSyncIdForCreateNew(
        tableName: String,
        objectId: UUID
    ): UUID? {
        return dao.getExistingSyncIdForCreateNew(tableName, objectId)
    }

    override suspend fun getExistingSyncIdForCreateNewOrUpdate(
        tableName: String,
        objectId: UUID
    ): UUID? {
        return dao.getExistingSyncIdForCreateNewOrUpdate(tableName, objectId)
    }

    override suspend fun getExistingSyncIdsForCreateNewOrUpdate(
        tableName: String,
        objectIds: List<UUID>
    ): Set<UUID> {
        return dao.getExistingSyncIdsForCreateNewOrUpdate(tableName, objectIds)
    }
}