package com.example.cukcuk.presentation.ui.statistic.statistic_main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cukcuk.domain.dtos.StatisticByInventory
import com.example.cukcuk.presentation.components.CukcukTextBox
import com.example.cukcuk.utils.FormatDisplay

@Composable
fun StatisticByInventoryBlock(
    statisticByInventory: List<StatisticByInventory>
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(10.dp)
                .background(
                    color = Color.White
                )
        ){
            Text("bieu do")
        }

        LazyColumn(
            modifier = Modifier
                .background(
                    color = Color.White
                )
                .padding(vertical = 6.dp)
        ) {
            itemsIndexed(statisticByInventory) { index, item ->
                StatisticByInventoryItem(item, index, statisticByInventory.size)
            }
        }

    }
}

@Composable
fun StatisticByInventoryItem(
    item: StatisticByInventory,
    index: Int,
    itemsSize: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CukcukTextBox(
            textValue = item.SortOrder.toString(),
            color = item.Color,
            size = 36
        )
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(start = 10.dp)
                .drawBehind{
                    val xStart = 0
                    val xEnd = size.width
                    val y = size.height
                    val strokeWidth = 1.dp.toPx()

                    if (index != itemsSize - 1) {
                        drawLine(
                            color = Color.Gray,
                            start = Offset(xStart.toFloat(), y),
                            end = Offset(xEnd, y),
                            strokeWidth = strokeWidth
                        )
                    }
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = item.InventoryName,
                    fontSize = 16.sp
                )
                Row {
                    Text(
                        text = FormatDisplay.formatNumber(item.Quantity.toString()),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(end = 6.dp)
                    )
                    Text(
                        text = item.UnitName,
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }
            Text(
                text = FormatDisplay.formatNumber(item.Amount.toString()),
                fontSize = 18.sp,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1f).padding(end = 10.dp)
            )
        }
    }
}