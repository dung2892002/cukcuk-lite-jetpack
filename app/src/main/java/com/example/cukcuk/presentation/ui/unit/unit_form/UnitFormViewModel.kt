package com.example.cukcuk.presentation.ui.unit.unit_form

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cukcuk.domain.model.Unit
import com.example.cukcuk.domain.usecase.unit.CreateUnitUseCase
import com.example.cukcuk.domain.usecase.unit.GetUnitDetailUseCase
import com.example.cukcuk.domain.usecase.unit.UpdateUnitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class UnitFormViewModel @Inject constructor(
    private val createUnitUseCase: CreateUnitUseCase,
    private val updateUnitUseCase: UpdateUnitUseCase,
    private val getUnitDetailUseCase: GetUnitDetailUseCase
) : ViewModel() {

    private val _unit = mutableStateOf<Unit>(Unit())
    val unit: State<Unit> = _unit

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _isShowForm = mutableStateOf(true)
    val isShowForm: State<Boolean> = _isShowForm

    fun fetchUnitDetail(unitId: UUID) {
        val fetchedUnit = getUnitDetailUseCase(unitId)
        _unit.value = fetchedUnit
    }

    fun submitForm() {
        viewModelScope.launch {
            val response = if (_unit.value.UnitID == null) {
                createUnitUseCase(_unit.value)
            } else {
                updateUnitUseCase(_unit.value)
            }
            _isShowForm.value = !response.isSuccess
            _errorMessage.value = response.message
        }
    }

    fun updateNewUnitName(name: String) {
        _unit.value = _unit.value.copy(UnitName = name)
    }

    fun resetValue() {
        _unit.value = Unit()
        _errorMessage.value = null
        _isShowForm.value = true
    }
}