package com.example.cukcuk.presentation.components

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.toColorInt
import com.example.cukcuk.domain.dtos.StatisticByInventory
import com.example.cukcuk.utils.FormatDisplay

import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.PercentFormatter

@SuppressLint("DefaultLocale")
@Composable
fun PieChartStatisticInventory(
    items: List<StatisticByInventory>,
    totalAmount: Double
) {


    val context = LocalContext.current
    val pieChart = PieChart(context)

    val mainParts = items.take(6)
    val otherParts = items.drop(6)
    val otherTotal = otherParts.sumOf { it.Percentage }

    val entries = mutableListOf<PieEntry>()
    val colors = mutableListOf<Int>()

    mainParts.forEach {
        entries.add(PieEntry(it.Percentage.toFloat(), "${String.format("%.1f", it.Percentage)}%"))
        colors.add(it.Color.toColorInt())
    }

    if (otherParts.isNotEmpty()) {
        entries.add(PieEntry(otherTotal.toFloat(), "${String.format("%.1f", otherTotal)}%"))
        colors.add(Color.GRAY)
    }

    val dataSet = PieDataSet(entries, "")
    dataSet.colors = colors

    // ✨ Hiển thị giá trị bên ngoài lát cắt và đường nối
    dataSet.setDrawValues(true)
    dataSet.valueTextColor = Color.BLACK
    dataSet.valueTextSize = 12f
    dataSet.valueFormatter = PercentFormatter(pieChart)

    dataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
    dataSet.xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
    dataSet.valueLinePart1Length = 0.4f
    dataSet.valueLinePart2Length = 0.2f
    dataSet.valueLineColor = Color.BLACK
    dataSet.valueLineWidth = 1f

    val pieData = PieData(dataSet)

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        factory = { context ->
            pieChart.apply {
                data = pieData
                description.isEnabled = false
                legend.isEnabled = false
                setUsePercentValues(true)
                setDrawEntryLabels(false)

                setHoleColor(Color.WHITE)
                isDrawHoleEnabled = true
                holeRadius = 75f
                transparentCircleRadius = 80f

                centerText = generateCenterText(totalAmount)
                setCenterTextSize(16f)
                setCenterTextColor(Color.BLACK)

                setExtraOffsets(5f, 10f, 5f, 10f)

                invalidate()
                animateY(1000, Easing.EaseInOutQuad)
            }
        }
    )
}

fun generateCenterText(totalAmount: Double): AnnotatedString {
    val line1 = "Tổng\ndoanh thu\n"
    val line2 = FormatDisplay.formatNumber(totalAmount.toString())

    return buildAnnotatedString {
        withStyle(style = SpanStyle(fontSize = 30.sp)) {
            append(line1)
        }
        withStyle(style = SpanStyle(fontSize = 40.sp)) {
            append(line2)
        }
    }
}