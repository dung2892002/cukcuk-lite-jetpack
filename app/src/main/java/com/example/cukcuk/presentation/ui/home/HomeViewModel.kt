package com.example.cukcuk.presentation.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cukcuk.domain.usecase.synchronize.GetCountSyncUseCase
import com.example.cukcuk.presentation.enums.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCountSyncUseCase: GetCountSyncUseCase
) : ViewModel() {

    private val _currentScreen = mutableStateOf(Screen.Sales)
    val currentScreen: State<Screen> = _currentScreen

    private val _title = mutableStateOf("Bán hàng")
    val title: State<String> = _title

    private val _showNavigationBar = mutableStateOf(false)
    val showNavigationBar: State<Boolean> = _showNavigationBar

    private val _countSync = mutableIntStateOf(0)
    val countSync: State<Int> = _countSync


    fun openNavigationView() {
        _showNavigationBar.value = true
        _countSync.intValue = getCountSyncUseCase()
    }

    fun closeNavigationView() {
        _showNavigationBar.value = false
    }

    fun navigateScreen(screen: Screen) {
        _currentScreen.value = screen
        _title.value = screen.displayName
    }
}