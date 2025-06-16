package com.example.presentation.ui.link_account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import com.example.presentation.R
import com.example.presentation.components.CukcukToolbar

@Composable
fun LinkAccountScreen(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            CukcukToolbar(
                title = "Liên kết tài khoản",
                menuTitle = null,
                onBackClick = { navController.popBackStack() },
                onMenuClick = {}
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(
                    color = colorResource(R.color.white)
                )
        ) {

        }
    }

}