package com.example.cukcuk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.example.cukcuk.presentation.navigation.AppNavHost
import com.example.cukcuk.presentation.theme.CukcukTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val config = resources.configuration
        val locale = Locale("vi")
        Locale.setDefault(locale)
        config.setLocale(locale)
        createConfigurationContext(config)

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
