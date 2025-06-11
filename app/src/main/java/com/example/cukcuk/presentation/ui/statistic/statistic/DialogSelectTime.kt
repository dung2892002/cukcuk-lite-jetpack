package com.example.cukcuk.presentation.ui.statistic.statistic

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.cukcuk.R
import com.example.cukcuk.presentation.components.CukcukButton
import com.example.cukcuk.presentation.components.CukcukDatePickerDialog
import com.example.cukcuk.utils.DateTimeHelper
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogSelectTime(
    onClose: () -> Unit,
    onSubmit: (LocalDateTime, LocalDateTime) -> Unit
) {

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    val defaultRange = DateTimeHelper.getThisMonth()
    var start by remember { mutableStateOf(defaultRange.first.toLocalDate()) }
    var end by remember { mutableStateOf(LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()))}

    var showFromDatePicker by remember { mutableStateOf(false) }
    var showToDatePicker by remember { mutableStateOf(false) }


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
                    text = stringResource(R.string.dialog_select_time_title),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = colorResource(R.color.main_color)
                        )
                        .padding(vertical = 6.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            showFromDatePicker = true
                        }
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.dialog_select_time_startDate_label),
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = start.format(formatter),
                        textAlign = TextAlign.End,
                        fontSize = 16.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_right),
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(
                    modifier = Modifier.fillMaxWidth().height(1.dp).background(color = Color.Gray)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            showToDatePicker = true
                        }
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.dialog_select_time_endDate_label),
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = end.format(formatter),
                        textAlign = TextAlign.End,
                        fontSize = 16.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_right),
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.LightGray
                        )
                        .padding(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)

                ) {
                    Spacer(modifier = Modifier.weight(2f))
                    CukcukButton(
                        title = stringResource(R.string.button_title_Cancel),
                        onClick = {
                            onClose()
                        },
                        bgColor = Color.White,
                        textColor = Color.Red,

                        )
                    CukcukButton(
                        title = stringResource(R.string.button_title_Accept),
                        onClick = {
                            onSubmit(start.atStartOfDay(), end.atTime(LocalTime.MAX))
                        },
                        bgColor = colorResource(R.color.main_color),
                        textColor = Color.White
                    )
                }
            }
        }
    }

    if (showFromDatePicker) {
        CukcukDatePickerDialog(
            input = start,
            onDismissRequest = {
                showFromDatePicker = false
            },
            onDateSelected = { year, month, dayOfMonth ->
                start = LocalDate.of(year, month + 1, dayOfMonth)
                showFromDatePicker = false
            }
        )
    }


    if (showToDatePicker) {
        CukcukDatePickerDialog(
            input = end,
            onDismissRequest = {
                showToDatePicker = false
            },
            onDateSelected = { year, month, dayOfMonth ->
                end = LocalDate.of(year, month + 1, dayOfMonth)
                showToDatePicker = false
            }
        )
    }

}

