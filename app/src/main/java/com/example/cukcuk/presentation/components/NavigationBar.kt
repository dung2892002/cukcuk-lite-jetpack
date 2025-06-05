package com.example.cukcuk.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cukcuk.R
import com.example.cukcuk.presentation.enums.NavItem
import com.example.cukcuk.presentation.enums.NavItemGroup
import com.example.cukcuk.presentation.enums.Screen
import com.example.cukcuk.utils.ImageHelper


@Composable
fun AppNavigationBarOverlay(
    currentScreen: Screen,
    onSelectScreen: (Screen) -> Unit,
    onSelectNewScreen: (NavItem) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    countSync: Int = 0
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
                .clickable { onClose() }
        )
        Column(
            modifier = Modifier
                .width(280.dp)
                .fillMaxHeight()
                .verticalScroll(scrollState)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorResource(R.color.main_color))
                    .padding(start = 20.dp, bottom = 20.dp, top = 4.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.default_avatar),
                    contentDescription = null,
                    modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(30.dp)
                        ),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = "Lê Văn Dũng",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 6.dp)
                )

                Text(
                    text = "lvdung@gmail.com",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
            AppNavigationBarContent(
                currentScreen = currentScreen,
                onSelectScreen = onSelectScreen,
                onSelectNewScreen = onSelectNewScreen,
                onClose = onClose,
                countSync = countSync
            )
        }
    }
}


@Composable
fun AppNavigationBarContent(
    currentScreen: Screen,
    onSelectScreen: (Screen) -> Unit,
    onSelectNewScreen: (NavItem) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    countSync: Int = 0
) {

    val screens = Screen.entries
    val navGroups = NavItemGroup.entries

    Surface(
        tonalElevation = 8.dp,
        shadowElevation = 4.dp,
        color = Color.White,
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            screens.forEach {
                NavigationItem(
                    label = it.displayName,
                    iconResId = it.iconResId,
                    selected = it == currentScreen,
                    onClick = {
                        onSelectScreen(it)
                        onClose()
                    }
                )
            }

            navGroups.forEach { group ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .drawBehind{
                            val strokeWidth = 1.dp.toPx()
                            val xStart = 0f
                            val xEnd = size.width
                            val y = 0f

                            drawLine(
                                color = Color.Gray,
                                start = Offset(xStart, y),
                                end = Offset(xEnd, y),
                                strokeWidth = strokeWidth
                            )
                        }
                ) {
                    Text(
                        text = group.label,
                        color = Color.Gray,
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth().padding(start = 10.dp)
                    )
                    group.items.forEach { item ->
                        NavigationItem(
                            label = item.label,
                            iconResId = item.iconResId,
                            selected = false,
                            onClick = { onSelectNewScreen(item) },
                            countSync = countSync,
                            showBadge = item == NavItem.SynchronizeData
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun NavigationItem(
    label: String,
    iconResId: Int,
    selected: Boolean,
    onClick: () -> Unit,
    countSync: Int = 0,
    showBadge: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (selected) colorResource(R.color.nav_item_selected) else Color.Transparent
            )
            .clickable(
                onClick = {onClick()}
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(iconResId),
            contentDescription = null,
            tint = Color.DarkGray,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            modifier = Modifier.weight(1f)
        )
        if (countSync > 0 && showBadge) {
            Box(
                modifier = Modifier
                    .width(18.dp)
                    .height(24.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(
                        color = colorResource(R.color.bg_sync_count)
                    ),
                contentAlignment = Alignment.Center
            )
            {
                Text(
                    text = countSync.toString(),
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}

