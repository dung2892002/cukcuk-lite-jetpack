package com.example.cukcuk

import android.app.Application
import android.content.Context
import com.example.cukcuk.di.dataModule
import com.example.cukcuk.di.domainModule
import com.example.cukcuk.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.io.FileOutputStream
import java.io.IOException


class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        copyDatabaseIfNotExists(this)

        startKoin {
            androidContext(this@MyApp)
            modules(
                listOf(
                    dataModule,
                    domainModule,
                    presentationModule
                )
            )
        }
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