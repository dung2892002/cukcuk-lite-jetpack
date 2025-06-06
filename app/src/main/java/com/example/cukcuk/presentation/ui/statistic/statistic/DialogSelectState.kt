package com.example.cukcuk.presentation.ui.statistic.statistic

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.cukcuk.R
import com.example.cukcuk.presentation.enums.StateStatistic
import com.example.cukcuk.presentation.theme.CukcukTheme

@Composable
fun DialogSelectState(
    onItemClick: (StateStatistic) -> Unit,
    onClose: () -> Unit,
    currentState: StateStatistic
) {
    val states = listOf(
        StateStatistic.Overview,
        StateStatistic.ThisWeek,
        StateStatistic.LastWeek,
        StateStatistic.ThisMonth,
        StateStatistic.LastMonth,
        StateStatistic.ThisYear,
        StateStatistic.LastYear,
        StateStatistic.Other)
    Dialog(
        onDismissRequest = {
            onClose()
        }
    ) {
        Surface(
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
        ) {
            Column(
                Modifier.background(Color.White)
            ) {
                Text(
                    text = "Thời gian",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = colorResource(R.color.main_color)
                        )
                        .padding(vertical = 6.dp)
                )

                states.forEach { state ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable{
                                onItemClick(state)
                            }
                            .drawBehind{
                                val xStart = 0f
                                val xEnd = size.width
                                val y = size.height
                                val strokeWidth = 1.dp.toPx()

                                if (state != StateStatistic.Other) {
                                    drawLine(
                                        color = Color.Gray,
                                        start = Offset(xStart, y),
                                        end = Offset(xEnd, y),
                                        strokeWidth = strokeWidth
                                    )
                                }
                            }
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = state.title)

                        if (state == currentState)
                            Icon(
                                painter = painterResource(R.drawable.ic_yes),
                                contentDescription = null,
                                tint = colorResource(R.color.main_color),
                                modifier = Modifier.size(20.dp)
                            )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DialogSelectStatePreview() {
    CukcukTheme {
        DialogSelectState(
            onItemClick = {},
            onClose = {},
            currentState = StateStatistic.Overview
        )
    }
}