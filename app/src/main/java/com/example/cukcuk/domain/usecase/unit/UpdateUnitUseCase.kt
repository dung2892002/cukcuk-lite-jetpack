package com.example.cukcuk.domain.usecase.unit

import com.example.cukcuk.domain.dtos.ResponseData
import com.example.cukcuk.domain.model.Unit
import com.example.cukcuk.domain.repository.UnitRepository
import java.time.LocalDateTime
import javax.inject.Inject

class UpdateUnitUseCase @Inject constructor(
    private val repository: UnitRepository
) {

    operator fun invoke(unit: Unit) : ResponseData {
        var response = ResponseData(false, "Có lỗi xảy ra")
        response.isSuccess = repository.checkExistUnitName(unit.UnitName, unit.UnitID)
        if (!response.isSuccess) {
            response.message = "Đơn vị tính ${unit.UnitName} đã tồn tại"
            return response
        }

        unit.ModifiedDate = LocalDateTime.now()
        response.isSuccess = repository.createUnit(unit)
        if (!response.isSuccess) {
            response.message = "Có lỗi xảy ra"
        }
        return response
    }
}