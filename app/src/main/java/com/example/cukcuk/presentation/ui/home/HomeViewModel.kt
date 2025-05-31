package com.example.cukcuk.presentation.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cukcuk.presentation.enums.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {
    private val _currentScreen = mutableStateOf(Screen.Sales)
    val currentScreen: State<Screen> = _currentScreen

    private val _title = mutableStateOf("Bán hàng")
    val title: State<String> = _title


    private val _showNavigationBar = mutableStateOf(false)
    val showNavigationBar: State<Boolean> = _showNavigationBar

    fun showNavigationView() {
        _showNavigationBar.value = true
    }

    fun hideNavigationView() {
        _showNavigationBar.value = false
    }

    fun navigateScreen(screen: Screen) {
        _currentScreen.value = screen
        _title.value = screen.displayName
    }
}