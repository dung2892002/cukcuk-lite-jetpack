package com.example.cukcuk.presentation.ui.statistic.statistic_by_inventory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cukcuk.presentation.components.Toolbar
import com.example.cukcuk.presentation.shared.SharedViewModel
import com.example.cukcuk.presentation.ui.statistic.statistic_main.StatisticByInventoryBlock


@Composable
fun StatisticByInventoryScreen(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    viewModel: StatisticByInventoryViewModel = hiltViewModel(),

) {
    val request = sharedViewModel.requestStatisticByInventory.value
    println(request)


    val statisticByInventory = viewModel.statisticByInventory.value

    Scaffold(
        topBar = {
            Toolbar(
                title = "Doanh thu theo mặt hàng",
                menuTitle = null,
                hasMenuIcon = false,
                onBackClick = {
                    navController.popBackStack()
                },
                onMenuClick = {}
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.LightGray
                )
                .padding(paddingValues)
        ) {
            if (request != null) {
                viewModel.handleStatistic(request.start, request.end)
                Text(
                    text = request.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White
                        )
                        .padding(vertical = 10.dp),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                StatisticByInventoryBlock(statisticByInventory)
            }
        }
    }
}