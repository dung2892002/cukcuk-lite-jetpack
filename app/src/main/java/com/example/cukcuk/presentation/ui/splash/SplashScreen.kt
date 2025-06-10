package com.example.cukcuk.presentation.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.cukcuk.R
import com.example.cukcuk.utils.SharedPrefManager

@Composable
fun SplashScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    val isLoggedIn = remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        isLoggedIn.value = SharedPrefManager.isLoggedIn(context)
        if (isLoggedIn.value) {
            navController.navigate("home") {
                popUpTo(0) {
                    inclusive = true
                }
            }
        } else {
            navController.navigate("login") {
                popUpTo(0) {
                    inclusive = true
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.main_color_bold)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            contentDescription = null,
            painter = painterResource(R.drawable.app_icon_white)
        )
    }
}