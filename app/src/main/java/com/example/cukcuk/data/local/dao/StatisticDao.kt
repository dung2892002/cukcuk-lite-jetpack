package com.example.cukcuk.data.local.dao

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.cukcuk.data.local.models.StatisticByInventory
import com.example.cukcuk.data.local.models.StatisticByDay
import com.example.cukcuk.data.local.models.StatisticByMonth
import com.example.cukcuk.data.local.models.StatisticOverView
import com.example.cukcuk.utils.getDouble
import com.example.cukcuk.utils.getString
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class StatisticDao @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val db: SQLiteDatabase by lazy {
        context.openOrCreateDatabase("cukcuk.db", Context.MODE_PRIVATE, null)
    }

    suspend fun getStatisticOverview(): List<StatisticOverView> = withContext(Dispatchers.IO) {
        val result = mutableListOf<StatisticOverView>()
        val query = """
            SELECT 'Yesterday' AS Title, 
                   SUM(CASE WHEN date(InvoiceDate) = date('now', '-1 day') THEN Amount ELSE 0 END) AS Amount
            FROM Invoice
            WHERE PaymentStatus = 1
            
            UNION ALL
            
            SELECT 'Today' AS Title, 
                   SUM(CASE WHEN date(InvoiceDate) = date('now') THEN Amount ELSE 0 END) AS Amount
            FROM Invoice
            WHERE PaymentStatus = 1
            
            UNION ALL
            
            SELECT 'ThisWeek' AS Title, 
                   SUM(CASE 
                        WHEN strftime('%W', InvoiceDate) = strftime('%W', 'now') 
                         AND strftime('%Y', InvoiceDate) = strftime('%Y', 'now')
                        THEN Amount ELSE 0 END) AS Amount
            FROM Invoice
            WHERE PaymentStatus = 1
            
            UNION ALL
            
            SELECT 'ThisMonth' AS Title, 
                   SUM(CASE 
                        WHEN strftime('%m', InvoiceDate) = strftime('%m', 'now') 
                         AND strftime('%Y', InvoiceDate) = strftime('%Y', 'now') 
                        THEN Amount ELSE 0 END) AS Amount
            FROM Invoice
            WHERE PaymentStatus = 1
            
            UNION ALL
            
            SELECT 'ThisYear' AS Title, 
                   SUM(CASE 
                        WHEN strftime('%Y', InvoiceDate) = strftime('%Y', 'now') 
                        THEN Amount ELSE 0 END) AS Amount
            FROM Invoice
            WHERE PaymentStatus = 1
                    
        """.trimIndent()

        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(query, null)
            while (cursor.moveToNext()) {
                result.add(
                    StatisticOverView(
                        Title = cursor.getString("Title"),
                        Amount = cursor.getDouble("Amount")
                    )
                )
            }

        }
        catch (ex: Exception) {
            ex.printStackTrace()
            println(ex)
        }
        finally {
            cursor?.close()
        }
        result
    }

    suspend fun getDailyStatisticOfWeek(startOfWeek: LocalDateTime, endOfWeek: LocalDateTime): List<StatisticByDay> = withContext(Dispatchers.IO) {
        val results = mutableListOf<StatisticByDay>()

        val query = """
            SELECT
                 date(InvoiceDate) as Day,
                 SUM(Amount) as Amount
            FROM Invoice
            WHERE PaymentStatus = 1 AND InvoiceDate BETWEEN ? AND ? 
            GROUP BY Day
            ORDER BY Day
        """.trimIndent()

        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(
                query,
                arrayOf(startOfWeek.toLocalDate().toString(), endOfWeek.toLocalDate().toString())
            )
            while (cursor.moveToNext()) {
                val day = LocalDate.parse(cursor.getString("Day"))
                results.add(
                    StatisticByDay(
                        Day = day,
                        TotalAmount = cursor.getDouble("Amount")
                    )
                )
            }
        }
        catch (ex: Exception) {
            ex.printStackTrace()
            println(ex)
        }
        finally {
            cursor?.close()
        }

        results.toList()
    }

    suspend fun getDailyStatisticOfMonth(start: LocalDateTime, end: LocalDateTime): List<StatisticByDay> = withContext(Dispatchers.IO) {
        val results = mutableListOf<StatisticByDay>()
        val query = """
            SELECT
                 date(InvoiceDate) as Day,
                 SUM(Amount) as Amount
            FROM Invoice
            WHERE PaymentStatus = 1 AND InvoiceDate BETWEEN ? AND ?
            GROUP BY Day
            ORDER BY Day
        """.trimIndent()

        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(
                query,
                arrayOf(start.toString(), end.toString())
            )
            while (cursor.moveToNext()) {
                val day = LocalDate.parse(cursor.getString("Day"))
                results.add(
                    StatisticByDay(
                        Day = day,
                        TotalAmount = cursor.getDouble("Amount")
                    )
                )
            }
        }
        catch (ex: Exception) {
            ex.printStackTrace()
            println(ex)
        }
        finally {
            cursor?.close()
        }

        results
    }

    suspend fun getMonthlyStatistic(start: LocalDateTime, end: LocalDateTime): List<StatisticByMonth> = withContext(Dispatchers.IO) {
        val results = mutableListOf<StatisticByMonth>()

        val query = """
            SELECT
                 strftime('%Y-%m', InvoiceDate) as Month,
                 SUM(Amount) as Amount
            FROM Invoice
            WHERE PaymentStatus = 1 AND InvoiceDate BETWEEN ? AND ?
            GROUP BY Month
            ORDER BY Month
        """.trimIndent()

        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(
                query,
                arrayOf(start.toString(), end.toString())
            )
            while (cursor.moveToNext()) {
                val month = cursor.getString("Month")
                val amount = cursor.getDouble("Amount")

                results.add(
                    StatisticByMonth(
                        TotalAmount = amount,
                        Month = month
                    )
                )
            }
        }
        catch (ex: Exception) {
            ex.printStackTrace()
            println(ex)
        }
        finally {
            cursor?.close()
        }

        results
    }

    suspend fun getStatisticByInventory(fromDate: LocalDateTime, toDate: LocalDateTime): List<StatisticByInventory> = withContext(Dispatchers.IO) {
        val query = """
            SELECT
                d.InventoryName as InventoryName,
                SUM(d.Quantity) as Quantity,
                SUM(d.Amount) as Amount,
                d.UnitName as UnitName
            FROM Invoice i
            INNER JOIN InvoiceDetail d ON i.InvoiceID = d.InvoiceID
            WHERE PaymentStatus = 1 AND i.InvoiceDate BETWEEN ? AND ?
            GROUP BY d.InventoryID, d.InventoryName, d.UnitName
            ORDER BY Amount DESC
        """.trimIndent()


        val result = mutableListOf<StatisticByInventory>()

        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(
                query,
                arrayOf(fromDate.toString(), toDate.toString())
            )
            while (cursor.moveToNext()) {
                result.add(
                    StatisticByInventory(
                        InventoryName = cursor.getString("InventoryName"),
                        Quantity = cursor.getDouble("Quantity"),
                        Amount = cursor.getDouble("Amount"),
                        UnitName = cursor.getString("UnitName"),
                    )
                )
            }
        }
        catch (ex: Exception) {
            ex.printStackTrace()
            println(ex)
        }
        finally {
            cursor?.close()
        }

        result
    }
}