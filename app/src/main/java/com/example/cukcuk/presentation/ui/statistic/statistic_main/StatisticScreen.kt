package com.example.cukcuk.presentation.ui.statistic.statistic_main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cukcuk.R
import com.example.cukcuk.presentation.enums.StateStatistic
import com.example.cukcuk.presentation.shared.SharedViewModel

@Composable
fun StatisticScreen(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    viewModel: StatisticViewModel = hiltViewModel(),
) {
    val currentState = viewModel.currentState.value
    val headerTitle = viewModel.title.value

    val statisticOverview = viewModel.statisticOverview.value
    val statisticByTime = viewModel.statisticByTime.value
    val statisticByInventory = viewModel.statisticByInventory.value

    val showDialogSelectState = viewModel.showDialogSelectState.value
    val showDialogSelectTime = viewModel.showDialogSelectTime.value

    val totalAmount = viewModel.totalAmount.value
    val lineChartLabels = viewModel.lineChartLabels.value

    LaunchedEffect(Unit) {
        viewModel.getStatisticOverview()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = colorResource(R.color.background_color)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White
                )
                .clickable{
                    viewModel.openDialogSelectState()
                }
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Thời gian",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Text(
                text = headerTitle,
                modifier = Modifier.weight(1f).padding(end = 6.dp),
                fontSize = 18.sp,
                textAlign = TextAlign.End,
            )
            Icon(
                painter = painterResource(R.drawable.ic_triangle),
                contentDescription = null,
                tint = Color.Black
            )
        }

        when (currentState) {
            StateStatistic.Overview -> {
                StatisticOverViewBlock(
                    onItemClick = {
                        if (it.Amount > 0.0) {
                            if (it.StatisticState == StateStatistic.Yesterday || it.StatisticState == StateStatistic.Today) {
                                val request = viewModel.createRequestByStatisticOverview(it)
                                sharedViewModel.setRequestStatisticByInventory(request)
                                navController.navigate("statistic_by_inventory")
                            } else {
                                viewModel.changeState(it.StatisticState)
                            }
                        }
                    },
                    statisticOverview = statisticOverview
                )
            }
            StateStatistic.Other -> {
                if (statisticByInventory.isNotEmpty() && totalAmount != 0.0) {
                    StatisticByInventoryBlock(
                        statisticByInventory = statisticByInventory,
                        totalAmount
                    )
                } else {
                    StatisticNoData()
                }
            }
            else -> {
                if (statisticByTime.isEmpty()) {
                    StatisticNoData()
                } else {
                    StatisticByTimeBlock(
                        statisticByTime = statisticByTime,
                        label = lineChartLabels,
                        onItemClick = {
                            val request = viewModel.createRequestByStatisticTime(it)
                            sharedViewModel.setRequestStatisticByInventory(request)
                            navController.navigate("statistic_by_inventory")
                        }
                    )
                }
            }
        }
    }

    if (showDialogSelectState) {
        DialogSelectState(
            onItemClick = {
                viewModel.changeState(it)
            },
            onClose = {
                viewModel.closeDialogSelectState()
            },
            currentState = currentState
        )
    }

    if (showDialogSelectTime) {
        DialogSelectTime(
            onSubmit = {
                start, end ->
                viewModel.handleStatisticByInventory(start, end)
            },
            onClose = {
                viewModel.closeDialogSelectTime()
            }
        )
    }
}


@Composable
fun StatisticNoData() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_chart_background),
            contentDescription = null,
            modifier = Modifier.size(60.dp),
            tint = Color.Gray
        )

        Text(
            text = "Báo cáo doanh thu không có số liệu"
        )
    }
}