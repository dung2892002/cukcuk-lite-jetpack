package com.example.cukcuk.presentation.ui.unit.unit_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
) : ViewModel() {

    private val _units = mutableStateOf<List<Unit>>(emptyList())
    val units: State<List<Unit>> = _units

    private val _unitUpdateID = mutableStateOf<UUID?>(null)
    val unitUpdateID: State<UUID?> = _unitUpdateID

    private val _isFormOpen = mutableStateOf(false)
    val isFormOpen: State<Boolean> = _isFormOpen

    init {
        loadUnits()
    }

    private fun loadUnits() {
        viewModelScope.launch {
            val data = getAllUnitUseCase()
            _units.value = data
            _unitUpdateID.value = null
        }
    }


    fun deleteUnit(unit: Unit) {
        val response = deleteUnitUseCase(unit)
        if (response.isSuccess) loadUnits()
    }

    fun backScreen() {
        println("backScreen")
    }

    fun openForm(unitId: UUID? = null) {
        _unitUpdateID.value = unitId
        _isFormOpen.value = true
    }

    fun closeForm(reload: Boolean) {
        _isFormOpen.value = false
        _unitUpdateID.value = null

        if(reload) loadUnits()
    }
}