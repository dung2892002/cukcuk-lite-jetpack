package com.example.cukcuk.utils

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext

object ImageHelper {
    fun getDrawableImageFromAssets(context: Context, imgName: String) : Drawable {
        val assetManager = context.assets
        val inputStream = assetManager.open("icon_default/$imgName") // thêm thư mục con
        val drawable = Drawable.createFromStream(inputStream, null)

        return drawable!!
    }

    fun getDrawableImageOtherFromAssets(context: Context, imgName: String) : Drawable {
        val assetManager = context.assets
        val inputStream = assetManager.open("icons/$imgName") // thêm thư mục con
        val drawable = Drawable.createFromStream(inputStream, null)

        return drawable!!
    }

    fun parseColor(hex: String): Color {
        val cleanHex = hex.removePrefix("#")
        val colorInt = cleanHex.toLong(16)
        return if (cleanHex.length == 6) {
            // RGB
            Color((0xFF shl 24) or colorInt.toInt())
        } else if (cleanHex.length == 8) {
            // ARGB
            Color(colorInt.toInt())
        } else {
            Color.Gray // fallback
        }
    }

    @Composable
    fun loadImageFromAssets(fileName: String): ImageBitmap? {
        val context = LocalContext.current
        return loadImageFromAssets(context, fileName)
    }

    fun loadImageFromAssets(context: Context, fileName: String): ImageBitmap? {
        return try {
            val inputStream = context.assets.open("icon_default/$fileName")
            val bitmap = BitmapFactory.decodeStream(inputStream)
            bitmap?.asImageBitmap()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}