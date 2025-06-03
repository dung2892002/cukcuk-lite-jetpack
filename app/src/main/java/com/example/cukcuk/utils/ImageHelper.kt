package com.example.cukcuk.utils

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withContext
import java.io.IOException

object ImageHelper {
    fun parseColor(hex: String): Color {
        val cleanHex = hex.removePrefix("#")
        val colorInt = cleanHex.toLong(16)
        return if (cleanHex.length == 6) {
            Color((0xFF shl 24) or colorInt.toInt())
        } else if (cleanHex.length == 8) {
            Color(colorInt.toInt())
        } else {
            Color.Gray
        }
    }

    fun loadImageFromAssets(context: Context, fileName: String): ImageBitmap? {
        return try {
            val inputStream = context.assets.open("icon_default/$fileName")
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            bitmap?.asImageBitmap()
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    fun loadOtherImageFromAssets(context: Context, fileName: String): ImageBitmap? {
        return try {
            val inputStream = context.assets.open("icons/$fileName")
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            bitmap?.asImageBitmap()
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    suspend fun loadAllImagesFromAssets(
        context: Context,
        folder: String
    ): List<Pair<String, ImageBitmap>> = withContext(Dispatchers.IO) {
        val assetManager = context.assets
        val fileNames = assetManager.list(folder) ?: return@withContext emptyList()

        // Giới hạn số lượng coroutine chạy cùng lúc (để tránh memory overload)
        val semaphore = Semaphore(4)

        coroutineScope {
            fileNames.map { fileName ->
                async {
                    semaphore.withPermit {
                        try {
                            assetManager.open("$folder/$fileName").use { inputStream ->
                                val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                                    val source = ImageDecoder.createSource(assetManager, "$folder/$fileName")
                                    ImageDecoder.decodeBitmap(source)
                                } else {
                                    BitmapFactory.decodeStream(inputStream)
                                }

                                bitmap?.asImageBitmap()?.let { imageBitmap ->
                                    fileName to imageBitmap
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            null
                        }
                    }
                }
            }.awaitAll().filterNotNull()
        }
    }


}