package com.example.cukcuk.presentation.ui.feedback

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import com.example.cukcuk.R
import com.example.cukcuk.presentation.components.Toolbar

@Composable
fun FeedbackScreen(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            Toolbar(
                title = "Góp ý với nhà phát triển",
                menuTitle = "Gửi",
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