package com.example.presentation.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.synchronize.GetCountSyncUseCase
import com.example.presentation.enums.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCountSyncUseCase: GetCountSyncUseCase
) : ViewModel() {

    private val _currentScreen = mutableStateOf(Screen.Sales)
    val currentScreen: State<Screen> = _currentScreen

    private val _title = mutableIntStateOf(Screen.Sales.label)
    val title: State<Int> = _title

    private val _showNavigationBar = mutableStateOf(false)
    val showNavigationBar: State<Boolean> = _showNavigationBar

    private val _countSync = mutableIntStateOf(0)
    val countSync: State<Int> = _countSync


    fun openNavigationView() {
        viewModelScope.launch {
            _countSync.intValue = getCountSyncUseCase()
            _showNavigationBar.value = true
        }
    }

    fun closeNavigationView() {
        _showNavigationBar.value = false
    }

    fun navigateScreen(screen: Screen) {
        _currentScreen.value = screen
        _title.intValue = screen.label
    }
}