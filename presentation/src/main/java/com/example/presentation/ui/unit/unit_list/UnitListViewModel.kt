package com.example.presentation.ui.unit.unit_list

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Unit
import com.example.domain.usecase.unit.DeleteUnitUseCase
import com.example.domain.usecase.unit.GetAllUnitUseCase
import com.example.presentation.mapper.getErrorMessage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID


class UnitListViewModel(
    private val getAllUnitUseCase: GetAllUnitUseCase,
    private val deleteUnitUseCase: DeleteUnitUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _units = mutableStateOf<List<Unit>>(emptyList())
    val units: State<List<Unit>> = _units

    private val _unitUpdate = mutableStateOf<Unit?>(null)
    val unitUpdate: State<Unit?> = _unitUpdate

    private val _unitSelected = mutableStateOf<Unit?>(null)
    val unitSelected: State<Unit?> = _unitSelected

    private val _isFormOpen = mutableStateOf(false)
    val isFormOpen: State<Boolean> = _isFormOpen

    private val _isOpenDialogDelete = mutableStateOf(false)
    val isOpenDialogDelete: State<Boolean> = _isOpenDialogDelete

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading


    init {
        viewModelScope.launch {
            loadUnits()
            val currentUnitId = savedStateHandle.get<String>("currentUnitId")?.let { UUID.fromString(it) }
            findUnitSelected(currentUnitId)
        }
    }

    private suspend fun loadUnits() {
        try {
            setLoading(true)
            delay(200)
            val data = getAllUnitUseCase()
            _units.value = data
            _unitUpdate.value = null
        }
        catch (e: Exception) {
            _errorMessage.value = e.message
        } finally {
            setLoading(false)
        }
    }

    fun findUnitSelected(unitId: UUID?) {
        _unitSelected.value = _units.value.find { it.UnitID == unitId }
    }

    fun selectUnit(unit: Unit) {
        _unitSelected.value = unit
    }


    fun deleteUnit(context: Context) {
        viewModelScope.launch {
            try {
                setLoading(true)
                delay(200)
                val response = deleteUnitUseCase(_unitUpdate.value!!)
                setErrorMessage(response.getErrorMessage(context))
                if (response.isSuccess) {
                    if (_unitUpdate.value?.UnitID == _unitSelected.value?.UnitID)
                        _unitSelected.value = null
                    closeFormAndDialog(true)
                }
            }
            catch (e: Exception) {
                setErrorMessage(e.message)
            } finally {
                _unitUpdate.value = null
                setLoading(false)
            }
        }
    }


    fun openForm(unit: Unit? = null) {
        _unitUpdate.value = unit
        _isFormOpen.value = true
    }

    fun closeFormAndDialog(reload: Boolean) {
        viewModelScope.launch {
            _unitUpdate.value = null
            _isFormOpen.value = false
            _isOpenDialogDelete.value = false

            if(reload) loadUnits()
        }
    }

    fun openDialogDelete(unit: Unit) {
        _unitUpdate.value = unit
        _isOpenDialogDelete.value = true
    }

    fun setErrorMessage(message: String?) {
        _errorMessage.value = message
    }

    fun setLoading(value: Boolean) {
        _loading.value = value
    }
}