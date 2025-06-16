package com.example.presentation.components

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.toColorInt
import com.example.domain.model.StatisticByInventory
import com.example.domain.utils.FormatDisplay
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.Utils

@SuppressLint("DefaultLocale")
@Composable
fun PieChartStatisticInventory(
    items: List<StatisticByInventory>,
    totalAmount: Double,
    context: Context
) {
    Utils.init(context)

    val mainParts = items.take(6)
    val otherParts = items.drop(6)
    val otherTotal = otherParts.sumOf { it.Percentage }

    val entries = mutableListOf<PieEntry>()
    val colors = mutableListOf<Int>()

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

    mainParts.forEach {
        entries.add(PieEntry(it.Percentage.toFloat()))
        colors.add(it.Color.toColorInt())
    }

    if (otherParts.isNotEmpty()) {
        entries.add(PieEntry(otherTotal.toFloat(), "${"%.1f".format(otherTotal)}%"))
        colors.add(Color.GRAY)
    }

    val dataSet = PieDataSet(entries, "").apply {
        this.colors = colors
        setDrawValues(true)
        valueTextColor = Color.BLACK
        valueTextSize = 12f
        yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        valueLinePart1Length = 0.4f
        valueLinePart2Length = 0.2f
        valueLineColor = Color.BLACK
        valueLineWidth = 1f
    }

    val pieData = PieData(dataSet).apply {
        setValueFormatter(object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return String.format("%.1f%%", value)
            }
        })
    }



    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        factory = { PieChart(context) },
        update = { pieChart ->
            pieChart.data = pieData
            pieChart.description.isEnabled = false
            pieChart.legend.isEnabled = false
            pieChart.setUsePercentValues(true)
            pieChart.setDrawEntryLabels(false)

            pieChart.setHoleColor(Color.WHITE)
            pieChart.isDrawHoleEnabled = true
            pieChart.holeRadius = 75f
            pieChart.transparentCircleRadius = 80f

            pieChart.centerText = generateCenterText(totalAmount)
            pieChart.setCenterTextSize(14f)
            pieChart.setCenterTextColor(Color.BLACK)
            pieChart.setExtraOffsets(5f, 10f, 5f, 10f)

            pieChart.post {
                pieChart.invalidate()
            }

            pieChart.animateY(1000, Easing.EaseInOutQuad)
        }
    )
}


