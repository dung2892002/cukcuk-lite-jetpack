package com.example.cukcuk.presentation.ui.synchronize

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cukcuk.domain.usecase.synchronize.GetCountSyncUseCase
import com.example.cukcuk.domain.usecase.synchronize.GetLastSyncTimeUseCase
import com.example.cukcuk.domain.usecase.synchronize.UpdateSyncDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class SynchronizeViewModel @Inject constructor(
    private val getCountSyncUseCase: GetCountSyncUseCase,
    private val getLastSyncTimeUseCase: GetLastSyncTimeUseCase,
    private val updateSyncDataUseCase: UpdateSyncDataUseCase
) : ViewModel() {
    private val _count = mutableIntStateOf(0)
    val count: State<Int> = _count

    private val _lastSyncTime = mutableStateOf<LocalDateTime?>(null)
    val lastSyncTime: State<LocalDateTime?> = _lastSyncTime

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    init {
        loadSyncData()
    }

    fun loadSyncData() {
        _count.intValue = getCountSyncUseCase()
        _lastSyncTime.value = getLastSyncTimeUseCase()
    }

    fun handleSyncData() {
        viewModelScope.launch {
            _loading.value = true
            delay(2500)
            updateSyncDataUseCase(LocalDateTime.now())
            loadSyncData()
            _loading.value = false
        }
    }
}