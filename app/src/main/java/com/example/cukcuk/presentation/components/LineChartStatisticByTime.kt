package com.example.cukcuk.presentation.components

import androidx.compose.runtime.Composable
import com.example.cukcuk.domain.dtos.StatisticByTime
import android.graphics.Color
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.cukcuk.presentation.enums.LineChartLabels
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
@Composable
fun LineChartStatisticByTime(
    statisticList: List<StatisticByTime>,
    label: LineChartLabels,
    labelMapper: (StatisticByTime) -> Int
) {
    val amountMap = mutableMapOf<Int, Float>()
    val xLabels = label.labels
    statisticList.forEach {
        val index = labelMapper(it)
        amountMap[index] = it.Amount.toFloat()
    }

    val entries = statisticList.map {
        val index = labelMapper(it)
        Entry(index.toFloat(), (it.Amount / 1000).toInt().toFloat())
    }


    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        factory = { context ->
            LineChart(context).apply {
                // Thiết lập cố định một lần
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                axisRight.isEnabled = false
                description.isEnabled = false
                legend.isEnabled = false
            }
        },
        update = { lineChart ->
            // Cập nhật dữ liệu mỗi lần Composable recomposes
            val dataSet = LineDataSet(entries, null).apply {
                color = Color.GREEN
                setCircleColors(Color.GREEN)
                setDrawCircleHole(false)
                valueTextColor = Color.BLACK
                lineWidth = 1f
                setDrawCircles(true)
                setDrawValues(false)
                mode = LineDataSet.Mode.LINEAR
            }

            lineChart.data = LineData(dataSet)

            lineChart.xAxis.apply {
                valueFormatter = IndexAxisValueFormatter(xLabels)
                labelCount = if (xLabels.size <= 12) xLabels.size else 10
                granularity = 1f
                axisMinimum = 0f
                axisMaximum = (xLabels.size - 1).toFloat()
                setDrawGridLines(false)
                setDrawAxisLine(false)
                setDrawLabels(true)
            }

            lineChart.axisLeft.apply {
                val maxAmount = statisticList.maxOfOrNull { it.Amount } ?: 0.0
                val roundedMax = ((maxAmount / 100000).toInt() + 1) * 100
                axisMinimum = 0f
                axisMaximum = roundedMax.toFloat()
                setDrawAxisLine(false)
                setDrawLabels(true)
                setDrawGridLines(true)
                setDrawZeroLine(false)
                zeroLineColor = Color.GRAY
                zeroLineWidth = 0.5f
                enableGridDashedLine(10f, 5f, 0f)
            }

            lineChart.invalidate()
        }
    )

}