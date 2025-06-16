package com.example.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.window.Dialog
import com.example.presentation.R
import com.example.presentation.theme.CukcukTheme

@Composable
fun CukcukLoadingDialog(
    title: String = ""
) {
    Dialog(
        onDismissRequest = {}
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(
                color = colorResource(R.color.main_color_bold),
                strokeWidth = 4.dp,
                modifier = Modifier.padding(horizontal = 30.dp)
            )
            Text(
                text = title,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview
@Composable
fun LoadingDialogPreview() {
    CukcukTheme {
        CukcukLoadingDialog(
            title = "Đang đồng bộ dữ liệu ..."
        )
    }
}