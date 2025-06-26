package com.example.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.R
import com.example.presentation.theme.CukcukTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CukcukToolbar(
    title: String,
    menuTitle: String?,
    hasMenuIcon: Boolean = false,
    onBackClick: () -> Unit,
    onMenuClick: () -> Unit,
    icon: Painter = painterResource(R.drawable.ic_back),
) {
    val isBackClickable = remember { mutableStateOf(true) }

    LaunchedEffect(isBackClickable.value) {
        if (!isBackClickable.value) {
            delay(1000)
            isBackClickable.value = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(R.color.main_color))
            .statusBarsPadding()
            .padding(start = 5.dp, end = 5.dp)
            .height(56.dp)
    ) {
        Icon(
            painter = icon,
            tint = Color.White,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable {
                    if (isBackClickable.value) {
                        isBackClickable.value = false
                        onBackClick()
                    }
                }
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
                        .widthIn(max = 80.dp),
                    overflow = TextOverflow.Ellipsis,
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
        CukcukToolbar(
            title = "Thống kê",
            menuTitle = null,
            hasMenuIcon = false,
            onBackClick = {},
            onMenuClick = {}
        )
    }
}