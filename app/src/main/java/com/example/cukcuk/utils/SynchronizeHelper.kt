package com.example.cukcuk.utils

import com.example.cukcuk.domain.model.InvoiceDetail
import com.example.cukcuk.domain.repository.SynchronizeRepository
import com.example.cukcuk.domain.enums.SynchronizeTable
import java.util.UUID
import javax.inject.Inject

class SynchronizeHelper @Inject constructor(
    private val syncRepository: SynchronizeRepository
) {
    suspend fun insertSync(table: SynchronizeTable, objectId: UUID?) {
        syncRepository.create(table.tableName, objectId!!, 0)
    }

    suspend fun updateSync(table: SynchronizeTable, objectId: UUID?) {
        val existingSyncId = syncRepository.getExistingSyncIdForCreateNewOrUpdate(table.tableName, objectId!!)
        if (existingSyncId == null) syncRepository.create(table.tableName, objectId, 1)
    }

    suspend fun deleteSync(table: SynchronizeTable, objectId: UUID?) {

        val existingSyncId = syncRepository.getExistingSyncIdForCreateNew(table.tableName, objectId!!)

        if (existingSyncId != null) syncRepository.delete(existingSyncId)
        else {
            syncRepository.deleteDataBeforeCreateDeleteSync(table.tableName, objectId)
            syncRepository.create(table.tableName, objectId, 2)
        }
    }

    suspend fun deleteSyncRange(table: SynchronizeTable, objectIds: List<UUID>) {
        val toDelete = mutableListOf<UUID>()
        val toCreate = mutableListOf<UUID>()

        for (objectId in objectIds) {
            val existingSyncId = syncRepository.getExistingSyncIdForCreateNew(table.tableName, objectId)

            if (existingSyncId != null) {
                toDelete.add(existingSyncId)
            } else {
                syncRepository.deleteDataBeforeCreateDeleteSync(table.tableName, objectId)
                toCreate.add(objectId)
            }
        }

        if (toDelete.isNotEmpty()) {
            syncRepository.deleteRange(toDelete)
        }
        if (toCreate.isNotEmpty()) {
            syncRepository.createRange(table.tableName, toCreate, 2)
        }
    }

    suspend fun createInvoiceDetail(details: MutableList<InvoiceDetail>){
        val ids = details.mapNotNull { it.InvoiceDetailID }
        syncRepository.createRange(SynchronizeTable.InvoiceDetail.tableName, ids, 0)
    }

    suspend fun deleteInvoiceDetail(details: List<InvoiceDetail>) {
        val ids = details.mapNotNull { it.InvoiceDetailID }
        deleteSyncRange(SynchronizeTable.InvoiceDetail, ids)
    }

    suspend fun updateInvoiceDetail(details: List<InvoiceDetail>) {
        val ids = details.mapNotNull { it.InvoiceDetailID }
        val existingIds = syncRepository.getExistingSyncIdsForCreateNewOrUpdate(SynchronizeTable.InvoiceDetail.tableName, ids)

        val toInsert = ids.filterNot { existingIds.contains(it) }

        if (toInsert.isNotEmpty()) {
            syncRepository.createRange(SynchronizeTable.InvoiceDetail.tableName, toInsert, 1)
        }
    }
}