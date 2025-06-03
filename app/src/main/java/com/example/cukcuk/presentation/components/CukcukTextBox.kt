package com.example.cukcuk.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.example.cukcuk.R
import com.example.cukcuk.presentation.theme.CukcukTheme
import com.example.cukcuk.utils.ImageHelper

@Composable
fun CukcukTextBox(
    colorRes: Color? = null,
    textValue: String = "",
    size: Int = 48,
    color: String? = null
) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .clip(RoundedCornerShape((size / 2).dp))
            .background(
                color = if (color != null) ImageHelper.parseColor(color) else colorRes!!
            ),
        contentAlignment = Alignment.Center
    )
    {
        if (textValue.isNotEmpty())
            Text(
                text = textValue,
                color = Color.White,
                fontSize = 20.sp
            )
    }
}


@Preview
@Composable
fun TextBoxPreview() {
    CukcukTheme {
        CukcukTextBox(
            colorRes = colorResource(R.color.color_unit_selected),
            textValue = "1"
        )
    }
}