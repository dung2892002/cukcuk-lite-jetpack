package com.example.presentation.ui.unit.unit_form

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Unit
import com.example.domain.usecase.unit.CreateUnitUseCase
import com.example.domain.usecase.unit.GetUnitDetailUseCase
import com.example.domain.usecase.unit.UpdateUnitUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID

class UnitFormViewModel(
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

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    private val _isDataLoaded = mutableStateOf(false)
    val isDataLoaded: State<Boolean> = _isDataLoaded

    fun fetchUnitDetail(unitId: UUID) {
        viewModelScope.launch {
            try {
                _loading.value = true
                delay(200)
                val fetchedUnit = getUnitDetailUseCase(unitId)
                _unit.value = fetchedUnit
            }
            catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _loading.value = false
                _isDataLoaded.value = true
                _isShowForm.value = true
            }
        }
    }

    fun submitForm() {
        viewModelScope.launch {
            try {
                _loading.value = true
                delay(200)
                val response = if (_unit.value.UnitID == null) {
                    createUnitUseCase(_unit.value)
                } else {
                    updateUnitUseCase(_unit.value)
                }
                _isShowForm.value = !response.isSuccess
                _errorMessage.value = response.message
            }
            catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun updateNewUnitName(name: String) {
        _unit.value = _unit.value.copy(UnitName = name)
    }

    fun resetValue() {
        _unit.value = Unit()
        _errorMessage.value = null
        _isShowForm.value = true
        _isDataLoaded.value = false
    }
}