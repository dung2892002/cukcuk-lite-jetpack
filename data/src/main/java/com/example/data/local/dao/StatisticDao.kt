package com.example.data.local.dao

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.data.local.models.StatisticByInventory
import com.example.data.local.models.StatisticByDay
import com.example.data.local.models.StatisticByMonth
import com.example.data.local.models.StatisticOverView
import com.example.data.local.getDouble
import com.example.data.local.getString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime

class StatisticDao(
    private val context: Context
) {
    private val db: SQLiteDatabase by lazy {
        context.openOrCreateDatabase("cukcuk.db", Context.MODE_PRIVATE, null)
    }

    suspend fun getStatisticOverview()
    : List<StatisticOverView> = withContext(Dispatchers.IO) {
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

    suspend fun getDailyStatisticOfWeek(startOfWeek: LocalDateTime, endOfWeek: LocalDateTime)
    : List<StatisticByDay> = withContext(Dispatchers.IO) {
        val results = mutableListOf<StatisticByDay>()
        val query = """
            WITH RECURSIVE dates(Day) AS (
              SELECT date(?)
              UNION ALL
              SELECT date(Day, '+1 day')
              FROM dates
              WHERE Day < date(?)
            )
            SELECT
              dates.Day AS Day,
              COALESCE(SUM(Invoice.Amount), 0) AS Amount
            FROM dates
            LEFT JOIN Invoice ON date(Invoice.InvoiceDate) = dates.Day AND Invoice.PaymentStatus = 1
            GROUP BY dates.Day
            ORDER BY dates.Day
        """.trimIndent()

        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(
                query,
                arrayOf(startOfWeek.toString(), endOfWeek.toString())
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

    suspend fun getDailyStatisticOfMonth(start: LocalDateTime, end: LocalDateTime)
    : List<StatisticByDay> = withContext(Dispatchers.IO) {
        val results = mutableListOf<StatisticByDay>()
        val query = """
            WITH RECURSIVE dates(Day) AS (
              SELECT date(?)
              UNION ALL
              SELECT date(Day, '+1 day')
              FROM dates
              WHERE Day < date(?)
            )
            SELECT
              dates.Day AS Day,
              COALESCE(SUM(Invoice.Amount), 0) AS Amount
            FROM dates
            LEFT JOIN Invoice ON date(Invoice.InvoiceDate) = dates.Day AND Invoice.PaymentStatus = 1
            GROUP BY dates.Day
            ORDER BY dates.Day
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

    suspend fun getMonthlyStatistic(start: LocalDateTime, end: LocalDateTime)
    : List<StatisticByMonth> = withContext(Dispatchers.IO) {
        val results = mutableListOf<StatisticByMonth>()

        val query = """
            WITH RECURSIVE months(Month) AS (
              SELECT strftime('%Y-%m', date(?))
              UNION ALL
              SELECT strftime('%Y-%m', date(Month || '-01', '+1 month'))
              FROM months
              WHERE Month < strftime('%Y-%m', date(?))
            )
            SELECT
                months.Month AS Month,
                COALESCE(SUM(Invoice.Amount), 0) AS Amount
            FROM months
            LEFT JOIN Invoice ON strftime('%Y-%m', Invoice.InvoiceDate) = months.Month AND Invoice.PaymentStatus = 1
            GROUP BY months.Month
            ORDER BY months.Month
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

    suspend fun getStatisticByInventory(fromDate: LocalDateTime, toDate: LocalDateTime)
    : List<StatisticByInventory> = withContext(Dispatchers.IO) {
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