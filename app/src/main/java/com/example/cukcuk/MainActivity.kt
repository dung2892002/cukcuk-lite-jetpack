package com.example.cukcuk

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.example.presentation.navigation.AppNavHost
import com.example.presentation.theme.CukcukTheme
import com.example.presentation.utils.LocaleHelper

class MainActivity : ComponentActivity() {
    override fun attachBaseContext(newBase: Context?) {
        val sharedPreferences = newBase?.getSharedPreferences("app_prefs", MODE_PRIVATE)
        val langCode = sharedPreferences?.getString("language_code", "vi") ?: "vi"
        val context = LocaleHelper.updateLocale(newBase!!, langCode)
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CukcukTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    AppNavHost(navController = rememberNavController())
                }
            }
        }
    }
}
