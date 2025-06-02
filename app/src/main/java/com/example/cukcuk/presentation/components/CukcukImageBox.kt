package com.example.cukcuk.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cukcuk.R
import com.example.cukcuk.presentation.theme.CukcukTheme
import com.example.cukcuk.utils.ImageHelper
import com.example.cukcuk.utils.ImageHelper.loadImageFromAssets

@Composable
fun CukcukImageBox(
    color: String = "",
    colorRes: Color? = null,
    imageName: String? = null,
    iconDrawable: Painter? = null,
    onClick: (() -> Unit)? = null,
    size: Int = 48,
    imageSize: Int = 36,
    tintIcon: Color = Color.Unspecified
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .size(size.dp)
            .clip(RoundedCornerShape((size / 2).dp))
            .background(colorRes ?: ImageHelper.parseColor(color))
            .clickable { onClick?.invoke() },
        contentAlignment = Alignment.Center
    )
    {
        if (imageName != null) {
            val imageBitmap: ImageBitmap? = loadImageFromAssets(context,imageName)
            imageBitmap?.let {
                Image(
                    bitmap = it,
                    contentDescription = null,
                    modifier = Modifier
                        .width(imageSize.dp)
                        .height(imageSize.dp)
                )
            }
        }
        if (iconDrawable != null) {
            Icon(
                painter = iconDrawable,
                contentDescription = null,
                tint = tintIcon,
                modifier = Modifier
                    .width(imageSize.dp)
                    .height(imageSize.dp)
            )
        }
    }
}


@Preview
@Composable
fun ImagePreview() {
    CukcukTheme {
        CukcukImageBox(
            color = "#00FF00",
            imageName = null,
            iconDrawable = painterResource(R.drawable.ic_colors),
            size = 48,
            imageSize = 36
        )
    }
}