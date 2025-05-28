package com.example.cukcuk.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    title: String,
    menuTitle: String?,
    hasMenuAction: Boolean = false,
    onBackClick: () -> Unit,     // => hàm không tham số trả về Unit
    onMenuClick: () -> Unit,
    navigationIcon: ImageVector = Icons.AutoMirrored.Filled.ArrowBack,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        },

        navigationIcon = {
            Icon(
                navigationIcon,
                tint = Color.White,
                contentDescription = "Toggle Drawer",
                modifier = Modifier.clickable{ onBackClick() }
            )
        },

        actions = {
            if (menuTitle != null) {
                Text(
                    text = menuTitle,
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier.clickable{ onMenuClick() }.padding(end = 10.dp))
            }
            if (hasMenuAction) {
                Icon(
                    Icons.Default.Add,
                    tint = Color.White,
                    contentDescription = "Thêm mới",
                    modifier = Modifier.clickable{ onMenuClick() }.padding(end = 10.dp))
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Blue, titleContentColor = Color.White)
    )
}