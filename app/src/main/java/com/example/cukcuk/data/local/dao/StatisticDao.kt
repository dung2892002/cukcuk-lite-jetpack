package com.example.cukcuk.data.local.dao

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.cukcuk.data.local.entities.ResultStatisticByInventory
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

    suspend fun getStatisticOverview(): List<Pair<String, Double>> = withContext(Dispatchers.IO) {
        val result = mutableListOf<Pair<String, Double>>()
        val query = """
            SELECT 'Yesterday' AS Label, 
                   SUM(CASE WHEN date(InvoiceDate) = date('now', '-1 day') THEN Amount ELSE 0 END) AS Value
            FROM Invoice
            WHERE PaymentStatus = 1
            
            UNION ALL
            
            SELECT 'Today' AS Label, 
                   SUM(CASE WHEN date(InvoiceDate) = date('now') THEN Amount ELSE 0 END) AS Value
            FROM Invoice
            WHERE PaymentStatus = 1
            
            UNION ALL
            
            SELECT 'ThisWeek' AS Label, 
                   SUM(CASE 
                        WHEN strftime('%W', InvoiceDate) = strftime('%W', 'now') 
                         AND strftime('%Y', InvoiceDate) = strftime('%Y', 'now')
                        THEN Amount ELSE 0 END) AS Value
            FROM Invoice
            WHERE PaymentStatus = 1
            
            UNION ALL
            
            SELECT 'ThisMonth' AS Label, 
                   SUM(CASE 
                        WHEN strftime('%m', InvoiceDate) = strftime('%m', 'now') 
                         AND strftime('%Y', InvoiceDate) = strftime('%Y', 'now') 
                        THEN Amount ELSE 0 END) AS Value
            FROM Invoice
            WHERE PaymentStatus = 1
            
            UNION ALL
            
            SELECT 'ThisYear' AS Label, 
                   SUM(CASE 
                        WHEN strftime('%Y', InvoiceDate) = strftime('%Y', 'now') 
                        THEN Amount ELSE 0 END) AS Value
            FROM Invoice
            WHERE PaymentStatus = 1
                    
        """.trimIndent()

        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(query, null)
            while (cursor.moveToNext()) {
                val label = cursor.getString(0)
                val value = cursor.getDouble(1)
                result.add(Pair(label, value))
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

    suspend fun getDailyStatisticOfWeek(startOfWeek: LocalDateTime, endOfWeek: LocalDateTime): Map<LocalDate, Double> = withContext(Dispatchers.IO) {
        val query = """
            SELECT
                 date(InvoiceDate) as day,
                 SUM(Amount) as Amount
            FROM Invoice
            WHERE PaymentStatus = 1 AND date(InvoiceDate) BETWEEN ? AND ? 
            GROUP BY day
            ORDER BY day
        """.trimIndent()

        var cursor: Cursor? = null

        val resultMap = mutableMapOf<LocalDate, Double>()
        try {
            cursor = db.rawQuery(
                query,
                arrayOf(startOfWeek.toLocalDate().toString(), endOfWeek.toLocalDate().toString())
            )
            while (cursor.moveToNext()) {
                val day = LocalDate.parse(cursor.getString(0))
                val amount = cursor.getDouble(1)
                resultMap[day] = amount
            }
        }
        catch (ex: Exception) {
            ex.printStackTrace()
            println(ex)
        }
        finally {
            cursor?.close()
        }

        resultMap.toMap()
    }

    suspend fun getDailyStatisticOfMonth(start: LocalDateTime, end: LocalDateTime): MutableMap<LocalDate, Double> = withContext(Dispatchers.IO) {
        val query = """
            SELECT
                 date(InvoiceDate) as day,
                 SUM(Amount) as Amount
            FROM Invoice
            WHERE PaymentStatus = 1 AND InvoiceDate BETWEEN ? AND ?
            GROUP BY day
            ORDER BY day
        """.trimIndent()

        var cursor: Cursor? = null

        val resultMap = mutableMapOf<LocalDate, Double>()
        try {
            cursor = db.rawQuery(
                query,
                arrayOf(start.toString(), end.toString())
            )
            while (cursor.moveToNext()) {
                val day = LocalDate.parse(cursor.getString(0))
                val amount = cursor.getDouble(1)
                resultMap[day] = amount
            }
        }
        catch (ex: Exception) {
            ex.printStackTrace()
            println(ex)
        }
        finally {
            cursor?.close()
        }

        resultMap
    }

    suspend fun getMonthlyStatistic(start: LocalDateTime, end: LocalDateTime): MutableMap<Pair<Int, Int>, Double> = withContext(Dispatchers.IO) {
        val startDate = start.toLocalDate()
        val endDate = end.toLocalDate()

        val query = """
            SELECT
                 strftime('%Y-%m', InvoiceDate) as month,
                 SUM(Amount) as Amount
            FROM Invoice
            WHERE PaymentStatus = 1 AND InvoiceDate BETWEEN ? AND ?
            GROUP BY month
            ORDER BY month
        """.trimIndent()

        var cursor: Cursor? = null
        val resultMap = mutableMapOf<Pair<Int, Int>, Double>() // (year, month) -> amount
        try {
            cursor = db.rawQuery(
                query,
                arrayOf(startDate.toString(), endDate.toString())
            )
            while (cursor.moveToNext()) {
                val monthStr = cursor.getString(0) // e.g., "2024-05"
                val parts = monthStr.split("-")
                val year = parts[0].toInt()
                val month = parts[1].toInt()
                val amount = cursor.getDouble(1)
                resultMap[Pair(year, month)] = amount
            }
        }
        catch (ex: Exception) {
            ex.printStackTrace()
            println(ex)
        }
        finally {
            cursor?.close()
        }

        resultMap
    }

    suspend fun getStatisticByInventory(fromDate: LocalDateTime, toDate: LocalDateTime): List<ResultStatisticByInventory> = withContext(Dispatchers.IO) {
        val query = """
            SELECT
                d.InventoryName,
                SUM(d.Quantity) as TotalQuantity,
                SUM(d.Amount) as TotalAmount,
                d.UnitName
            FROM Invoice i
            INNER JOIN InvoiceDetail d ON i.InvoiceID = d.InvoiceID
            WHERE PaymentStatus = 1 AND date(i.InvoiceDate) BETWEEN ? AND ?
            GROUP BY d.InventoryID, d.InventoryName, d.UnitName
            ORDER BY TotalAmount DESC
        """.trimIndent()


        val list = mutableListOf<ResultStatisticByInventory>()

        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(
                query,
                arrayOf(fromDate.toLocalDate().toString(), toDate.toLocalDate().toString())
            )
            while (cursor.moveToNext()) {
                val name = cursor.getString(0)
                val quantity = cursor.getDouble(1)
                val amount = cursor.getDouble(2)
                val unit = cursor.getString(3)

                list.add(
                    ResultStatisticByInventory(
                        InventoryName = name,
                        Quantity = quantity,
                        Amount = amount,
                        UnitName = unit,
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

        list
    }
}