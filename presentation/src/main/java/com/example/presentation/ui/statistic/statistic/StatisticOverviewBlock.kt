package com.example.presentation.ui.statistic.statistic

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.sp
import com.example.presentation.R
import com.example.domain.model.StatisticOverview
import com.example.presentation.components.CukcukImageBox
import com.example.domain.utils.FormatDisplay

@Composable
fun StatisticOverViewBlock(
    onItemClick: (StatisticOverview, index: Int) -> Unit,
    statisticOverview: List<StatisticOverview>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(color = Color.White)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(4.dp),
                color = Color.Gray
            )
            .padding(vertical = 6.dp)
    ) {
        statisticOverview.forEachIndexed { index,item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .clickable {
                        if (item.Amount > 0.0) onItemClick(item, index)
                    }
                    .padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CukcukImageBox(
                    color = item.Color,
                    imageName = item.IconFile,
                    fromIconDefaultFolder = false,
                    size = 36,
                    imageSize = 24
                )
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(start = 10.dp)
                        .drawBehind{
                            val xStart = 0
                            val xEnd = size.width
                            val y = size.height - 1f
                            val strokeWidth = 1.dp.toPx()

                            if (index != statisticOverview.size - 1) {
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
                    Text(
                        text = item.Title,
                        modifier = Modifier.weight(1f),
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Text(
                        text = FormatDisplay.formatNumber(item.Amount.toString()),
                        color = colorResource(R.color.main_color)
                    )
                    Icon(
                        contentDescription = null,
                        painter = painterResource(R.drawable.ic_arrow_right),
                        tint = Color.Gray,
                        modifier = Modifier.size(34.dp).padding(end = 10.dp)
                    )
                }
            }
        }
    }
}