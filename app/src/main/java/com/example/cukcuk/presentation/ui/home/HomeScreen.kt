package com.example.cukcuk.presentation.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cukcuk.presentation.components.AppNavigationBarOverlay
import com.example.cukcuk.presentation.components.Toolbar
import com.example.cukcuk.presentation.enums.Screen
import com.example.cukcuk.presentation.shared.SharedViewModel
import com.example.cukcuk.presentation.ui.inventory.inventory_list.InventoryListScreen
import com.example.cukcuk.presentation.ui.invoice.invoice_list.InvoiceListScreen
import com.example.cukcuk.presentation.ui.statistic.statistic_main.StatisticScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    var showNavigationBar = viewModel.showNavigationBar.value
    var currentScreen = viewModel.currentScreen.value
    var title = viewModel.title.value

    val scaffoldContent = @Composable {
        Scaffold(
            Modifier.background(Color.White),
            topBar = {
                Toolbar(
                    title = title,
                    menuTitle = null,
                    hasMenuIcon = currentScreen!= Screen.Statistics,
                    onBackClick = {viewModel.showNavigationView()},
                    onMenuClick = {
                        if (currentScreen == Screen.Sales) {
                            navController.navigate("invoice_form")
                        } else navController.navigate("inventory_form")
                    },
                    navigationIcon = Icons.Default.Menu
                )
            }
        )
        { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color.White)
            ) {
                when (currentScreen) {
                    Screen.Sales -> InvoiceListScreen(navController)
                    Screen.Menu -> InventoryListScreen(navController)
                    Screen.Statistics -> StatisticScreen(navController, sharedViewModel)
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        scaffoldContent()

        if (showNavigationBar) {
            AppNavigationBarOverlay(
                currentScreen = currentScreen,
                onSelectScreen = { viewModel.navigateScreen(it) },
                onClose = { viewModel.hideNavigationView() },
                modifier = Modifier.zIndex(1f)
            )
        }
    }

}

