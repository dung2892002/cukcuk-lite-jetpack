package com.example.cukcuk.data.local.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.cukcuk.data.local.models.InventoryEntity
import com.example.cukcuk.data.local.models.InvoiceDetailEntity
import com.example.cukcuk.data.local.models.InvoiceEntity
import com.example.cukcuk.utils.getBoolean
import com.example.cukcuk.utils.getDateTime
import com.example.cukcuk.utils.getDateTimeOrNull
import com.example.cukcuk.utils.getDouble
import com.example.cukcuk.utils.getInt
import com.example.cukcuk.utils.getString
import com.example.cukcuk.utils.getUUID
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

class InvoiceDao  @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val db: SQLiteDatabase by lazy {
        context.openOrCreateDatabase("cukcuk.db", Context.MODE_PRIVATE, null)
    }
    suspend fun getNewInvoiceNo()
    : String = withContext(Dispatchers.IO) {
        var count = 0
        val query = """
            SELECT COUNT(*) FROM Invoice WHERE PaymentStatus = 1
        """.trimIndent()
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0)
            }
        }
        catch (ex: Exception) {
            ex.printStackTrace()
            println(ex)
        }
        finally {
            cursor?.close()
        }

        String.format(Locale.US, "%05d", count + 1)
    }

    suspend fun getListInvoiceNotPayment()
    : MutableList<InvoiceEntity> = withContext(Dispatchers.IO) {
        val invoices = mutableListOf<InvoiceEntity>()
        val query = """
            SELECT InvoiceID, InvoiceDate, Amount, NumberOfPeople, TableName, ListItemName, InvoiceDate, ReceiveAmount
            FROM Invoice 
            WHERE PaymentStatus = 0 
            ORDER BY CreatedDate DESC
        """.trimIndent()

        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(query, null)
            while (cursor.moveToNext()) {
                val invoice = InvoiceEntity(
                    InvoiceID = cursor.getUUID("InvoiceID"),
                    InvoiceDate = cursor.getDateTimeOrNull("InvoiceDate"),
                    Amount = cursor.getDouble("Amount"),
                    ReceiveAmount = cursor.getDouble("ReceiveAmount"),
                    NumberOfPeople = cursor.getInt("NumberOfPeople"),
                    TableName = cursor.getString("TableName"),
                    ListItemName = cursor.getString("ListItemName"),
                )
                invoices.add(invoice)
            }
        }
        catch (ex: Exception) {
            ex.printStackTrace()
            println(ex)
        }
        finally {
            cursor?.close()
        }
        invoices
    }

    suspend fun getListInvoicesDetail(invoiceId: UUID)
    : MutableList<InvoiceDetailEntity> = withContext(Dispatchers.IO) {
        val invoicesDetail = mutableListOf<InvoiceDetailEntity>()
        val query = """
            SELECT 
                InvoiceDetailID, InvoiceID, InventoryID, InventoryName, UnitID, UnitName,
                Quantity, UnitPrice, Amount, SortOrder
            FROM InvoiceDetail 
            WHERE InvoiceID = ? 
            ORDER BY SortOrder
        """.trimIndent()

        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(query, arrayOf(invoiceId.toString()))
            while (cursor.moveToNext()) {
                val invoiceDetail = InvoiceDetailEntity(
                    InvoiceDetailID = cursor.getUUID("InvoiceDetailID"),
                    InvoiceID = cursor.getUUID("InvoiceID"),
                    InventoryID = cursor.getUUID("InventoryID"),
                    InventoryName = cursor.getString("InventoryName"),
                    UnitID = cursor.getUUID("UnitID"),
                    UnitName = cursor.getString("UnitName"),
                    Quantity = cursor.getDouble("Quantity"),
                    UnitPrice = cursor.getDouble("UnitPrice"),
                    Amount = cursor.getDouble("Amount"),
                    SortOrder = cursor.getInt("SortOrder"),
                )
                invoicesDetail.add(invoiceDetail)
            }
        }
        catch (ex: Exception) {
            ex.printStackTrace()
            println(ex)
        }
        finally {
            cursor?.close()
        }
        invoicesDetail
    }

    suspend fun deleteInvoice(invoiceId: String)
    : Boolean = withContext(Dispatchers.IO){
         try {
            db.beginTransaction()
            db.delete(
                "InvoiceDetail",
                "InvoiceID = ?",
                arrayOf(invoiceId)
            )
            db.delete(
                "Invoice",
                "InvoiceID = ?",
                arrayOf(invoiceId)
            )

            db.setTransactionSuccessful()
            true
        } catch (ex: Exception) {
            ex.printStackTrace()
            println(ex)
            false
        } finally {
            db.endTransaction()
        }
    }

    suspend fun getInvoiceById(invoiceId: UUID)
    : InvoiceEntity? = withContext(Dispatchers.IO) {
        var invoice: InvoiceEntity? = null
        val invoiceQuery = "SELECT * FROM Invoice WHERE InvoiceID = ?"
        val cursor = db.rawQuery(invoiceQuery, arrayOf(invoiceId.toString()))

        if (cursor.moveToFirst()) {
            invoice = InvoiceEntity(
                InvoiceID = cursor.getUUID("InvoiceID"),
                InvoiceType = cursor.getInt("InvoiceType"),
                InvoiceDate = cursor.getDateTimeOrNull("InvoiceDate"),
                Amount = cursor.getDouble("Amount"),
                ReceiveAmount = cursor.getDouble("ReceiveAmount"),
                ReturnAmount = cursor.getDouble("ReturnAmount"),
                PaymentStatus = cursor.getInt("PaymentStatus"),
                NumberOfPeople = cursor.getInt("NumberOfPeople"),
                TableName = cursor.getString("TableName"),
                ListItemName = cursor.getString("ListItemName"),
            )
        }
        cursor.close()
        invoice
    }

    suspend fun getInvoiceDetailById(invoiceDetailId: UUID)
    : InvoiceDetailEntity? = withContext(Dispatchers.IO) {
        var invoiceDetail: InvoiceDetailEntity? = null
        val invoiceQuery = "SELECT * FROM InvoiceDetail WHERE InvoiceDetailID = ?"
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(invoiceQuery, arrayOf(invoiceDetailId.toString()))
            if (cursor.moveToFirst()) {
                invoiceDetail = InvoiceDetailEntity(
                    InvoiceDetailID = cursor.getUUID("InvoiceDetailID"),
                    InvoiceDetailType = cursor.getInt("InvoiceDetailType"),
                    InvoiceID = cursor.getUUID("InvoiceID"),
                    InventoryID = cursor.getUUID("InventoryID"),
                    InventoryName = cursor.getString("InventoryName"),
                    UnitID = cursor.getUUID("UnitID"),
                    UnitName = cursor.getString("UnitName"),
                    Quantity = cursor.getDouble("Quantity"),
                    UnitPrice = cursor.getDouble("UnitPrice"),
                    Amount = cursor.getDouble("Amount"),
                    Description = cursor.getString("Description"),
                    SortOrder = cursor.getInt("SortOrder"),
                    CreatedDate = cursor.getDateTime("CreatedDate"),
                    CreatedBy = cursor.getString("CreatedBy"),
                    ModifiedDate = cursor.getDateTime("ModifiedDate"),
                    ModifiedBy = cursor.getString("ModifiedBy")
                )
            }
        }
        catch (ex: Exception) {
            println(ex)
            ex.printStackTrace()
        }
        finally {
            cursor?.close()
        }

        invoiceDetail
    }

    suspend fun getAllInventoryInactive()
    : MutableList<InventoryEntity>  = withContext(Dispatchers.IO) {
        val query =""" 
            SELECT 
                i.InventoryID, i.InventoryName, i.Price,
                i.Inactive, i.Color, i.IconFileName,
                u.UnitId, u.UnitName
            FROM 
                Inventory i
                JOIN Unit u ON i.UnitID = u.UnitId
            WHERE i.Inactive = 1
        """

        var cursor: Cursor? = null
        val inventoryList = mutableListOf<InventoryEntity>()

        try {
            cursor = db.rawQuery(query,null)
            while (cursor.moveToNext()) {
                val inventory = InventoryEntity(
                    InventoryID = cursor.getUUID("InventoryID"),
                    InventoryName = cursor.getString("InventoryName"),
                    Price = cursor.getDouble("Price"),
                    Inactive = cursor.getBoolean("Inactive"),
                    Color = cursor.getString("Color"),
                    IconFileName = cursor.getString("IconFileName"),
                    UnitID = cursor.getUUID("UnitID"),
                    UnitName = cursor.getString("UnitName")
                )

                inventoryList.add(inventory)
            }
        }
        catch (ex: Exception) {
            ex.printStackTrace()
            println(ex)
        }
        finally {
            cursor?.close()
        }
        inventoryList
    }

    suspend fun createInvoice(invoice: InvoiceEntity, details: List<InvoiceDetailEntity>)
    : Boolean  = withContext(Dispatchers.IO){
        try {
            db.beginTransaction()
            insertInvoice(invoice)
            insertInvoiceDetailRange(details)
            db.setTransactionSuccessful()
            true
        } catch (ex: Exception) {
            ex.printStackTrace()
            println(ex)
            false
        } finally {
            db.endTransaction()
        }
    }

    suspend fun updateInvoice(invoice: InvoiceEntity,
                              newsDetail: MutableList<InvoiceDetailEntity>,
                              updatesDetail: MutableList<InvoiceDetailEntity>,
                              deletesDetail: MutableList<InvoiceDetailEntity>)
    : Boolean = withContext(Dispatchers.IO){
        try {
            db.beginTransaction()
            updateInvoiceOnly(invoice)
            insertInvoiceDetailRange(newsDetail)
            updateInvoiceDetailRange(updatesDetail)
            deleteInvoiceDetailRange(deletesDetail.map { it.InvoiceDetailID!! })

            db.setTransactionSuccessful()
            true
        } catch (ex: Exception) {
            ex.printStackTrace()
            println(ex)
            false
        } finally {
            db.endTransaction()
        }
    }

    suspend fun paymentInvoice(invoice: InvoiceEntity)
    : Boolean = withContext(Dispatchers.IO) {
        try {
            db.beginTransaction()
            val values = ContentValues().apply {
                put("InvoiceDate", invoice.InvoiceDate.toString())
                put("ReceiveAmount", invoice.ReceiveAmount)
                put("ReturnAmount", invoice.ReturnAmount)
                put("RemainAmount", invoice.RemainAmount)
                put("InvoiceNo", invoice.InvoiceNo)
                put("PaymentStatus", 1)
                put("ModifiedDate", invoice.ModifiedDate.toString())
            }
            db.update("Invoice", values, "InvoiceID = ?", arrayOf(invoice.InvoiceID.toString()))
            db.setTransactionSuccessful()
            true
        } catch (ex: Exception) {
            ex.printStackTrace()
            println(ex)
            false
        } finally {
            db.endTransaction()
        }
    }

    private suspend fun insertInvoice(invoice: InvoiceEntity)
    = withContext(Dispatchers.IO) {
        val values = ContentValues().apply {
            put("InvoiceID", invoice.InvoiceID.toString())
            put("InvoiceNo", invoice.InvoiceNo)
            put("InvoiceDate", invoice.InvoiceDate.toString())
            put("Amount", invoice.Amount)
            put("ReceiveAmount", invoice.ReceiveAmount)
            put("ReturnAmount", invoice.ReturnAmount)
            put("RemainAmount", invoice.RemainAmount)
            put("PaymentStatus", invoice.PaymentStatus)
            put("NumberOfPeople", invoice.NumberOfPeople)
            put("TableName", invoice.TableName)
            put("ListItemName", invoice.ListItemName)
            put("CreatedDate", invoice.CreatedDate.toString())
        }
        db.insert("Invoice", null, values)
    }

    private suspend fun insertInvoiceDetailRange(details: List<InvoiceDetailEntity>)
    = withContext(Dispatchers.IO) {
        for (detail in details) {
            val values = ContentValues().apply {
                put("InvoiceDetailID", detail.InvoiceDetailID.toString())
                put("InvoiceID", detail.InvoiceID.toString())
                put("InventoryID", detail.InventoryID.toString())
                put("InventoryName", detail.InventoryName)
                put("UnitID", detail.UnitID?.toString())
                put("UnitName", detail.UnitName)
                put("Quantity", detail.Quantity)
                put("UnitPrice", detail.UnitPrice)
                put("Amount", detail.Amount)
                put("Description", detail.Description)
                put("SortOrder", detail.SortOrder)
                put("CreatedDate", detail.CreatedDate.toString())
            }

            db.insert("InvoiceDetail", null, values)
        }
    }

    private suspend fun updateInvoiceOnly(invoice: InvoiceEntity)
    = withContext(Dispatchers.IO) {
        val values = ContentValues().apply {
            put("Amount", invoice.Amount)
            put("ReceiveAmount", invoice.ReceiveAmount)
            put("NumberOfPeople", invoice.NumberOfPeople)
            put("TableName", invoice.TableName)
            put("ListItemName", invoice.ListItemName)
            put("ModifiedDate", invoice.ModifiedDate.toString())
        }

        db.update(
            "Invoice",
            values,
            "InvoiceID = ?",
            arrayOf(invoice.InvoiceID.toString())
        )
    }

    private suspend fun updateInvoiceDetailRange(details: List<InvoiceDetailEntity>)
    = withContext(Dispatchers.IO) {
        val sql = """
        UPDATE InvoiceDetail SET
            InvoiceDetailType = ?,
            InventoryID = ?,
            InventoryName = ?,
            UnitID = ?,
            UnitName = ?,
            Quantity = ?,
            UnitPrice = ?,
            Amount = ?,
            Description = ?,
            SortOrder = ?,
            ModifiedDate = ?,
            ModifiedBy = ?
        WHERE InvoiceDetailID = ?
    """.trimIndent()

        val statement = db.compileStatement(sql)

        try {
            db.beginTransaction()
            for (detail in details) {
                statement.clearBindings()

                statement.bindLong(1, detail.InvoiceDetailType.toLong())
                statement.bindString(2, detail.InventoryID.toString())
                statement.bindString(3, detail.InventoryName)
                statement.bindString(4, detail.UnitID.toString())
                statement.bindString(5, detail.UnitName )
                statement.bindDouble(6, detail.Quantity)
                statement.bindDouble(7, detail.UnitPrice)
                statement.bindDouble(8, detail.Amount)
                statement.bindString(9, detail.Description)
                statement.bindLong(10, detail.SortOrder.toLong())
                statement.bindString(11, detail.ModifiedDate.toString())
                statement.bindString(12, detail.ModifiedBy )
                statement.bindString(13, detail.InvoiceDetailID.toString())

                statement.executeUpdateDelete()
            }
            db.setTransactionSuccessful()
        } catch (ex: Exception) {
            ex.printStackTrace()
            println(ex)
        } finally {
            db.endTransaction()
        }
    }

    private suspend fun deleteInvoiceDetailRange(detailIds: List<UUID>)
    = withContext(Dispatchers.IO) {
        val sql = "DELETE FROM InvoiceDetail WHERE InvoiceDetailID = ?"
        val statement = db.compileStatement(sql)

        try {
            db.beginTransaction()
            for (id in detailIds) {
                statement.clearBindings()
                statement.bindString(1, id.toString())
                statement.executeUpdateDelete()
            }
            db.setTransactionSuccessful()
        } catch (ex: Exception) {
            ex.printStackTrace()
            println(ex)
        } finally {
            db.endTransaction()
        }
    }
}