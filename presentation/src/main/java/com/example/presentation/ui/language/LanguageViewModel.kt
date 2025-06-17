package com.example.presentation.ui.language

import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.core.content.edit

class LanguageViewModel(
    application: Application
) : ViewModel() {
    private val sharedPreferences = application.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    private val _language = mutableStateOf("")
    val language: State<String> = _language

    init {
        loadLanguage()
    }

    private fun loadLanguage() {
        _language.value = getSavedLanguage()
    }

    private fun getSavedLanguage(): String {
        return sharedPreferences.getString("language_code", "vi") ?: "vi"
    }

    fun setLanguage(newLanguageCode: String) {
        if (_language.value != newLanguageCode) {
            sharedPreferences.edit { putString("language_code", newLanguageCode) }
            _language.value = newLanguageCode
        }
    }
}