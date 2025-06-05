package com.example.cukcuk.presentation.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cukcuk.R
import com.example.cukcuk.presentation.components.AppNavigationBarOverlay
import com.example.cukcuk.presentation.components.Toolbar
import com.example.cukcuk.presentation.enums.NavItem
import com.example.cukcuk.presentation.enums.Screen
import com.example.cukcuk.presentation.shared.SharedViewModel
import com.example.cukcuk.presentation.ui.inventory.inventory_list.InventoryListScreen
import com.example.cukcuk.presentation.ui.invoice.invoice_list.InvoiceListScreen
import com.example.cukcuk.presentation.ui.statistic.statistic_main.StatisticScreen
import com.example.cukcuk.utils.SharedPrefManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    sharedViewModel: SharedViewModel,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    var showNavigationBar = viewModel.showNavigationBar.value
    var currentScreen = viewModel.currentScreen.value
    var title = viewModel.title.value
    var countSync = viewModel.countSync.value

    fun navigateToScreen(navItem: NavItem) {
        when (navItem) {
            NavItem.SharedWithFriend -> println("SharedWithFriend")
            NavItem.RatingApp -> println("RatingApp")
            NavItem.Logout -> {
                SharedPrefManager.setLoggedIn(context, false)
                navController.navigate(navItem.route) {
                    popUpTo(0) {inclusive = true}
                    launchSingleTop = true
                }
            }
            else -> navController.navigate(navItem.route)
        }
        viewModel.closeNavigationView()
    }

    val scaffoldContent = @Composable {
        Scaffold(
            Modifier.background(Color.White),
            topBar = {
                Toolbar(
                    title = title,
                    menuTitle = null,
                    hasMenuIcon = currentScreen!= Screen.Statistics,
                    onBackClick = {viewModel.openNavigationView()},
                    onMenuClick = {
                        if (currentScreen == Screen.Sales) {
                            navController.navigate("invoice_form")
                        } else navController.navigate("inventory_form")
                    },
                    icon = painterResource(R.drawable.ic_menu)
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
                onSelectNewScreen = { navigateToScreen(it) },
                onClose = { viewModel.closeNavigationView() },
                modifier = Modifier.zIndex(1f),
                countSync = countSync
            )
        }
    }
}

