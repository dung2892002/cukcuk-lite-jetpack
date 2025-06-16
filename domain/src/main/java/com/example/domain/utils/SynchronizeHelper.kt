package com.example.domain.utils

import com.example.domain.model.InvoiceDetail
import com.example.domain.repository.SynchronizeRepository
import com.example.domain.enums.SynchronizeTable
import java.util.UUID


//cập nhật dữ liệu đồng bộ
class SynchronizeHelper (
    private val syncRepository: SynchronizeRepository
) {

    //tạo bản ghi khi thêm mới
    suspend fun insertSync(table: SynchronizeTable, objectId: UUID?) {
        syncRepository.create(table.tableName, objectId!!, 0)
    }


    // tạo bản ghi khi cập nhật
    suspend fun updateSync(table: SynchronizeTable, objectId: UUID?) {
        val existingSyncId = syncRepository.getExistingSyncIdForCreateNewOrUpdate(table.tableName, objectId!!)
        if (existingSyncId == null) syncRepository.create(table.tableName, objectId, 1)
    }


    // tạo bản ghi khi xóa
    suspend fun deleteSync(table: SynchronizeTable, objectId: UUID?) {

        val existingSyncId = syncRepository.getExistingSyncIdForCreateNew(table.tableName, objectId!!)

        if (existingSyncId != null) syncRepository.delete(existingSyncId)
        else {
            syncRepository.deleteDataBeforeCreateDeleteSync(table.tableName, objectId)
            syncRepository.create(table.tableName, objectId, 2)
        }
    }

    // xóa bản ghi đồng bộ theo danh sách objectIds
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

    // tạo bản ghi đồng bộ cho InvoiceDetail khi thêm mới
    suspend fun createInvoiceDetail(details: MutableList<InvoiceDetail>){
        val ids = details.mapNotNull { it.InvoiceDetailID }
        syncRepository.createRange(SynchronizeTable.InvoiceDetail.tableName, ids, 0)
    }

    // cập nhật bản ghi đồng bộ cho InvoiceDetail khi xóa
    suspend fun deleteInvoiceDetail(details: List<InvoiceDetail>) {
        val ids = details.mapNotNull { it.InvoiceDetailID }
        deleteSyncRange(SynchronizeTable.InvoiceDetail, ids)
    }

    // cập nhật bản ghi đồng bộ cho InvoiceDetail khi cập nhật
    suspend fun updateInvoiceDetail(details: List<InvoiceDetail>) {
        val ids = details.mapNotNull { it.InvoiceDetailID }
        val existingIds = syncRepository.getExistingSyncIdsForCreateNewOrUpdate(SynchronizeTable.InvoiceDetail.tableName, ids)

        val toInsert = ids.filterNot { existingIds.contains(it) }

        if (toInsert.isNotEmpty()) {
            syncRepository.createRange(SynchronizeTable.InvoiceDetail.tableName, toInsert, 1)
        }
    }
}