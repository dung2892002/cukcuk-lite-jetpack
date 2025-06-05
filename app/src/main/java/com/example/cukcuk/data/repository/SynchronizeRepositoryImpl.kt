package com.example.cukcuk.data.repository

import com.example.cukcuk.data.local.dao.SynchronizeDao
import com.example.cukcuk.domain.model.SynchronizeData
import com.example.cukcuk.domain.repository.SynchronizeRepository
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class SynchronizeRepositoryImpl @Inject constructor(
    private val dao: SynchronizeDao
) : SynchronizeRepository {

    override fun deleteAll() {
        dao.deleteAll()
    }

    override fun countSync(): Int {
        return dao.countSync()
    }

    override fun getLastSyncTime(): LocalDateTime? {
        return dao.getLastSyncTime()
    }

    override fun updateLastSyncTime(lastSyncTime: LocalDateTime) {
        dao.updateLastSyncTime(lastSyncTime)
    }

    override fun getAllSync(): MutableList<SynchronizeData> {
        return dao.getAllSync()
    }

    override fun create(tableName: String, objectId: UUID, action: Int) {
        dao.create(tableName, objectId, action)
    }

    override fun delete(syncId: UUID) {
        dao.delete(syncId)
    }

    override fun createRange(
        tableName: String,
        objectIds: List<UUID>,
        action: Int
    ) {
        dao.createRange(tableName, objectIds, action)
    }

    override fun deleteRange(syncIds: List<UUID>) {
        dao.deleteRange(syncIds)
    }

    override fun deleteDataBeforeCreateDeleteSync(
        tableName: String,
        objectId: UUID
    ) {
        dao.deleteDataBeforeCreateDeleteSync(tableName, objectId)
    }

    override fun getExistingSyncIdForCreateNew(
        tableName: String,
        objectId: UUID
    ): UUID? {
        return dao.getExistingSyncIdForCreateNew(tableName, objectId)
    }

    override fun getExistingSyncIdForCreateNewOrUpdate(
        tableName: String,
        objectId: UUID
    ): UUID? {
        return dao.getExistingSyncIdForCreateNewOrUpdate(tableName, objectId)
    }

    override fun getExistingSyncIdsForCreateNewOrUpdate(
        tableName: String,
        objectIds: List<UUID>
    ): Set<UUID> {
        return dao.getExistingSyncIdsForCreateNewOrUpdate(tableName, objectIds)
    }
}