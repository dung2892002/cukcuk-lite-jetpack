package com.example.cukcuk.domain.usecase.unit

import com.example.cukcuk.domain.dtos.ResponseData
import com.example.cukcuk.domain.model.Unit
import com.example.cukcuk.domain.repository.UnitRepository
import com.example.cukcuk.utils.SynchronizeHelper
import javax.inject.Inject

class DeleteUnitUseCase @Inject constructor(
    private val repository: UnitRepository,
    private val syncHelper: SynchronizeHelper
){
    operator fun invoke(unit: Unit) : ResponseData {
        var response = ResponseData(false, "Có lỗi xảy ra")

        response.isSuccess = !repository.checkUseByInventory(unit.UnitID!!)
        if (!response.isSuccess) {
            response.message = "Đơn vị tính <${unit.UnitName}> đang được sử dụng"
            return response
        }

        response.isSuccess = repository.deleteUnit(unit.UnitID!!)
        if (response.isSuccess) {
            response.message = null
            syncHelper.deleteSync("Unit", unit.UnitID)
        }
        return response
    }
}