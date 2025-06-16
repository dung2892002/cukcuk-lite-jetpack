package com.example.domain.usecase.unit

import com.example.domain.model.ResponseData
import com.example.domain.model.Unit
import com.example.domain.repository.UnitRepository
import com.example.domain.enums.SynchronizeTable
import com.example.domain.utils.SynchronizeHelper

class DeleteUnitUseCase(
    private val repository: UnitRepository,
    private val syncHelper: SynchronizeHelper
){
    suspend operator fun invoke(unit: Unit) : ResponseData {
        var response = ResponseData(false, "Có lỗi xảy ra")

        response.isSuccess = !repository.checkUseByInventory(unit.UnitID!!)
        if (!response.isSuccess) {
            response.message = "Đơn vị tính <${unit.UnitName}> đang được sử dụng"
            return response
        }

        response.isSuccess = repository.deleteUnit(unit.UnitID!!)
        if (response.isSuccess) {
            response.message = null
            syncHelper.deleteSync(SynchronizeTable.Unit, unit.UnitID)
        }
        return response
    }
}