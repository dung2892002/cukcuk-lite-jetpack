package com.example.presentation.components

import android.app.DatePickerDialog
import android.view.ContextThemeWrapper
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.presentation.R

@Composable
fun CukcukDatePickerDialog(
    year: Int,
    month: Int,
    day: Int,
    onDismissRequest: () -> Unit,
    onDateSelected: (year: Int, month: Int, dayOfMonth: Int) -> Unit,
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val dialog = DatePickerDialog(
            ContextThemeWrapper(context, R.style.CustomDatePickerDialogTheme),
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                onDateSelected(selectedYear, selectedMonth, selectedDayOfMonth)
                onDismissRequest()
            },
            year,
            month,
            day
        )

        dialog.show()

        dialog.setOnShowListener {
            dialog.getButton(DatePickerDialog.BUTTON_POSITIVE)
                ?.setTextColor(ContextCompat.getColor(context, R.color.main_color))
            dialog.getButton(DatePickerDialog.BUTTON_NEGATIVE)?.apply {
                text = "HỦY"
                setTextColor(ContextCompat.getColor(context, R.color.main_color))
            }
        }
    }
}

