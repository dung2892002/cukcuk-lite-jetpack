package com.example.cukcuk.presentation.ui.unit.unit_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cukcuk.domain.model.Unit
import com.example.cukcuk.domain.usecase.unit.DeleteUnitUseCase
import com.example.cukcuk.domain.usecase.unit.GetAllUnitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class UnitListViewModel @Inject constructor(
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


    init {
        loadUnits()
        val currentUnitId = savedStateHandle.get<String>("currentUnitId")?.let { UUID.fromString(it) }
        findUnitSelected(currentUnitId)
    }

    private fun loadUnits() {
        viewModelScope.launch {
            val data = getAllUnitUseCase()
            _units.value = data
            _unitUpdate.value = null
        }
    }

    fun findUnitSelected(unitId: UUID?) {
        _unitSelected.value = _units.value.find { it.UnitID == unitId }
    }

    fun selectUnit(unit: Unit) {
        _unitSelected.value = unit
    }


    fun deleteUnit() {
        val response = deleteUnitUseCase(_unitUpdate.value!!)
        setErrorMessage(response.message)
        if (response.isSuccess) {
            if (_unitUpdate.value?.UnitID == _unitSelected.value?.UnitID)
                _unitSelected.value = null
            closeFormAndDialog(true)
        }
    }


    fun openForm(unit: Unit? = null) {
        _unitUpdate.value = unit
        _isFormOpen.value = true
    }

    fun closeFormAndDialog(reload: Boolean) {
        _unitUpdate.value = null
        _isFormOpen.value = false
        _isOpenDialogDelete.value = false

        if(reload) loadUnits()
    }

    fun openDialogDelete(unit: Unit) {
        _unitUpdate.value = unit
        _isOpenDialogDelete.value = true
    }

    fun setErrorMessage(message: String?) {
        _errorMessage.value = message
    }

    fun buildDialogContent() : AnnotatedString {
        return buildAnnotatedString {
            append("Bạn có chắc muốn xóa đơn vị tính ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append(unitUpdate.value?.UnitName)
            }
            append(" không?")
        }
    }
}