package com.example.cukcuk

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import java.io.FileOutputStream
import java.io.IOException


@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        copyDatabaseIfNotExists(this)
    }

    private fun copyDatabaseIfNotExists(context: Context) {
        val assetDbName = "cukcuk_blank.db"
        val appDbName = "cukcuk.db"
        val dbPath = context.getDatabasePath(appDbName)

        if (!dbPath.exists()) {
            try {
                dbPath.parentFile?.mkdirs()
                context.assets.open(assetDbName).use { inputStream ->
                    FileOutputStream(dbPath).use { outputStream ->
                        val buffer = ByteArray(1024)
                        var length: Int
                        while (inputStream.read(buffer).also { length = it } > 0) {
                            outputStream.write(buffer, 0, length)
                        }
                        outputStream.flush()
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}