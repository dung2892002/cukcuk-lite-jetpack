package com.example.cukcuk.presentation.ui.statistic.statistic_main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cukcuk.R
import com.example.cukcuk.domain.dtos.StatisticByTime
import com.example.cukcuk.utils.FormatDisplay

@Composable
fun StatisticByTimeBlock(
    statisticByTime: List<StatisticByTime>,
    onItemClick: (StatisticByTime) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 10.dp),

        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White
                )
                .height(180.dp)
        ){
            Text("bieu do")
        }


        LazyColumn(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(
                    color = Color.White
                )
        ) {
            itemsIndexed(statisticByTime) { index, item ->
                StatisticByTimeItem(
                    item,
                    index,
                    statisticByTime.size,
                    onClick = {
                        onItemClick(item)
                    })
            }
        }

    }
}

@Composable
fun StatisticByTimeItem(
    item: StatisticByTime,
    index: Int,
    itemsSize: Int,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp)
            .clickable{
                onClick()
            }
            .padding(start = 6.dp)
            .drawBehind{
                val strokeWidth = 1.dp.toPx()
                val xStart = 0f
                val xEnd = size.width
                val y = size.height
                if (index < itemsSize - 1) {
                    drawLine(
                        color = Color.Gray,
                        start = Offset(xStart,y),
                        end = Offset(xEnd,y),
                        strokeWidth = strokeWidth
                    )
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = item.Title,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = FormatDisplay.formatNumber(item.Amount.toString()),
            color = colorResource(R.color.main_color),
        )

        Icon(
            painter = painterResource(R.drawable.ic_arrow_right),
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(34.dp).padding(end = 10.dp)
        )
    }
}