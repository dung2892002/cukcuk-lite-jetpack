package com.example.cukcuk.presentation.components

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.view.ContextThemeWrapper
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import java.util.*
import com.example.cukcuk.R
import com.example.cukcuk.presentation.theme.CukcukTheme
import java.time.LocalDate
import java.time.ZoneId

@SuppressLint("DiscouragedApi")
@Composable
fun CukcukDatePickerDialog(
    input: LocalDate,
    onDismissRequest: () -> Unit,
    onDateSelected: (year: Int, month: Int, dayOfMonth: Int) -> Unit,
) {
    val context = LocalContext.current

    val calendar = Calendar.getInstance().apply {
        val instant = input.atStartOfDay(ZoneId.systemDefault()).toInstant()
        time = Date.from(instant)
    }


    val datePickerDialog = remember(context) {
        DatePickerDialog(
            ContextThemeWrapper(context, R.style.CustomDatePickerDialogTheme),
            { _, year, month, dayOfMonth ->
                onDateSelected(year, month, dayOfMonth)
                onDismissRequest()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    datePickerDialog.show()


    val positiveButton = datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE)
    positiveButton.setTextColor(ContextCompat.getColor(context, R.color.main_color))

    val negativeButton = datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE)
    negativeButton.text = "HỦY"
    negativeButton.setTextColor(ContextCompat.getColor(context, R.color.main_color))
}


@Preview
@Composable
fun CukcukDatePickerDialogPreview() {
    CukcukTheme {
        CukcukDatePickerDialog(
            input = LocalDate.now(),
            onDismissRequest = {},
            onDateSelected = { _, _, _ -> }
        )
    }
}