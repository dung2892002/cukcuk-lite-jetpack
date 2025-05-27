package com.example.cukcuk.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.cukcuk.presentation.enums.Screen


@Composable
fun AppNavigationBarOverlay(
    currentScreen: Screen,
    onSelectScreen: (Screen) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        // Click ra ngoài để đóng
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
                .clickable { onClose() }
        )

        // Menu bên trái
        AppNavigationBarContent(
            currentScreen = currentScreen,
            onSelectScreen = onSelectScreen,
            onClose = onClose,
            modifier = Modifier.align(Alignment.CenterStart)
        )
    }
}


@Composable
fun AppNavigationBarContent(
    currentScreen: Screen,
    onSelectScreen: (Screen) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        tonalElevation = 8.dp,
        shadowElevation = 4.dp,
        color = MaterialTheme.colorScheme.surface,
        modifier = modifier
            .width(240.dp)
            .fillMaxHeight()
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Chức năng", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 16.dp))

            NavigationItem(
                label = "Bán hàng",
                selected = currentScreen == Screen.Sales,
                onClick = {
                    onSelectScreen(Screen.Sales)
                    onClose()
                }
            )

            NavigationItem(
                label = "Thực đơn",
                selected = currentScreen == Screen.Menu,
                onClick = {
                    onSelectScreen(Screen.Menu)
                    onClose()
                }
            )

            NavigationItem(
                label = "Thống kê",
                selected = currentScreen == Screen.Statistics,
                onClick = {
                    onSelectScreen(Screen.Statistics)
                    onClose()
                }
            )
        }
    }
}


@Composable
private fun NavigationItem(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        label = { Text(label) },
        selected = selected,
        onClick = onClick
    )
}

