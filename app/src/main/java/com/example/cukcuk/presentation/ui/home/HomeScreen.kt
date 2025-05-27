package com.example.cukcuk.presentation.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cukcuk.presentation.components.AppNavigationBarOverlay
import com.example.cukcuk.presentation.enums.Screen
import com.example.cukcuk.presentation.ui.inventory.inventory_list.InventoryListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    var showNavigationBar = viewModel.showNavigationBar.value
    var currentScreen = viewModel.currentScreen.value
    var title = viewModel.title.value

    val scaffoldContent = @Composable {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(title, color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { viewModel.showNavigationView() }) {
                            Icon(Icons.Default.Menu,tint = Color.White, contentDescription = "Toggle Drawer")
                        }
                    },
                    actions = {
                        if (currentScreen != Screen.Statistics) {
                            IconButton(onClick = {
                                    if (currentScreen == Screen.Sales) {
                                        navController.navigate("invoice_form")
                                    } else navController.navigate("inventory_form")
                                }
                            ) {
                                Icon(Icons.Default.Add,tint = Color.White, contentDescription = "Thêm mới")
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Blue,
                        titleContentColor = Color.White
                    )
                )

            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (currentScreen) {
                    Screen.Sales -> Text("Màn hình Bán hàng", Modifier.align(Alignment.Center))
                    Screen.Menu -> InventoryListScreen(navController)
                    Screen.Statistics -> Text("Màn hình Thống kê", Modifier.align(Alignment.Center))
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

