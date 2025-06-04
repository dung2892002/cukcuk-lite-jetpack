package com.example.cukcuk.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cukcuk.R
import com.example.cukcuk.presentation.theme.CukcukTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    title: String,
    menuTitle: String?,
    hasMenuIcon: Boolean = false,
    onBackClick: () -> Unit,
    onMenuClick: () -> Unit,
    icon: Painter = painterResource(R.drawable.ic_back),
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(R.color.main_color))
            .padding(top = 20.dp, start = 5.dp, end = 5.dp)
            .height(56.dp)
    ) {
        Icon(
            painter = icon,
            tint = Color.White,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable { onBackClick() }
                .padding(10.dp)
        )

        Text(
            text = title,
            color = Color.White,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )

        Box(
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            if (menuTitle != null) {
                Text(
                    text = menuTitle,
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .clickable { onMenuClick() }
                        .padding(10.dp)
                )
            }

            if (hasMenuIcon) {
                Icon(
                    painter = painterResource(R.drawable.icon_add),
                    tint = Color.White,
                    contentDescription = "Thêm mới",
                    modifier = Modifier
                        .clickable { onMenuClick() }
                        .padding(10.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun ToolbarPreview() {
    CukcukTheme {
        Toolbar(
            title = "Thống kê",
            menuTitle = null,
            hasMenuIcon = false,
            onBackClick = {},
            onMenuClick = {}
        )
    }
}