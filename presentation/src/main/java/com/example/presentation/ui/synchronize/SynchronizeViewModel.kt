package com.example.presentation.ui.synchronize

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.synchronize.GetCountSyncUseCase
import com.example.domain.usecase.synchronize.GetLastSyncTimeUseCase
import com.example.domain.usecase.synchronize.UpdateSyncDataUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class SynchronizeViewModel(
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
        viewModelScope.launch {
            val defCount = async { getCountSyncUseCase() }
            val defLastSyncTime = async { getLastSyncTimeUseCase() }
            _count.intValue = defCount.await()
            _lastSyncTime.value = defLastSyncTime.await()
        }
    }

    fun handleSyncData() {
        viewModelScope.launch {
            _loading.value = true
            delay(1000)
            updateSyncDataUseCase(LocalDateTime.now())
            loadSyncData()
            _loading.value = false
        }
    }
}