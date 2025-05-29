package com.example.cukcuk.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CukcukButton(
    title: String,
    onClick: () -> Unit,
    bgColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = bgColor),
        onClick = onClick,
        shape = RoundedCornerShape(4.dp),
        contentPadding = PaddingValues(10.dp)

    ) {
        Text(
            text = title,
            color = textColor,
            softWrap = false)
    }
}

