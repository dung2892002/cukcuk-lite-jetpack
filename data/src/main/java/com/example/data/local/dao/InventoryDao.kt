package com.example.data.local.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.data.local.getBoolean
import com.example.data.local.getDouble
import com.example.data.local.getString
import com.example.data.local.getUUID
import com.example.data.local.models.InventoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class InventoryDao (
    private val context: Context
) {
    private val db: SQLiteDatabase by lazy {
        context.openOrCreateDatabase("cukcuk.db", Context.MODE_PRIVATE, null)
    }

    suspend fun getAllInventory()
    : List<InventoryEntity>  = withContext(Dispatchers.IO) {
        val query =""" 
            SELECT 
                i.InventoryID, i.InventoryName, i.Price,
                i.Inactive, i.Color, i.IconFileName,
                u.UnitId, u.UnitName
            FROM 
                Inventory i
                JOIN Unit u ON i.UnitID = u.UnitId
        """.trimIndent()
        val inventoryList = mutableListOf<InventoryEntity>()
        var cursor: Cursor? = null

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
        } catch (ex: Exception) {
            ex.printStackTrace()
            println(ex)
        } finally {
            cursor?.close()
        }
        inventoryList
    }

    suspend fun getInventoryById(inventoryID: UUID)
    : InventoryEntity? = withContext(Dispatchers.IO) {
        val query = """
            SELECT 
                i.InventoryID, i.InventoryName, i.Price,
                i.Inactive, i.Color, i.IconFileName,
                u.UnitId, u.UnitName
            FROM 
                Inventory i
                JOIN Unit u ON i.UnitID = u.UnitId
            WHERE i.InventoryID = ?
        """.trimIndent()

        var inventory: InventoryEntity? = null
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(query, arrayOf(inventoryID.toString()))
            if (cursor.moveToFirst()) {
                inventory = InventoryEntity(
                    InventoryID = cursor.getUUID("InventoryID"),
                    InventoryName = cursor.getString("InventoryName"),
                    Price = cursor.getDouble("Price"),
                    Inactive = cursor.getBoolean("Inactive"),
                    Color = cursor.getString("Color"),
                    IconFileName = cursor.getString("IconFileName"),
                    UnitID = cursor.getUUID("UnitID"),
                    UnitName = cursor.getString("UnitName")
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
        inventory
    }

    suspend fun createInventory(inventory: InventoryEntity)
    : Boolean = withContext(Dispatchers.IO) {
        try {
            val values = ContentValues().apply {
                put("InventoryID", inventory.InventoryID.toString())
                put("InventoryName", inventory.InventoryName)
                put("Price", inventory.Price)
                put("Inactive", 1)
                put("CreatedDate", inventory.CreatedDate?.toString())
                put("Color", inventory.Color)
                put("IconFileName", inventory.IconFileName)
                put("UnitID", inventory.UnitID.toString())
            }

            val result = db.insert("Inventory", null, values)
            result != -1L
        } catch (ex: Exception) {
            ex.printStackTrace()
            println(ex)
            false
        }
    }

    suspend fun updateInventory(inventory: InventoryEntity)
    : Boolean = withContext(Dispatchers.IO) {
        if (inventory.InventoryID == null) false

        try {
            val values = ContentValues().apply {
                put("InventoryName", inventory.InventoryName)
                put("Price", inventory.Price)
                put("Inactive", if (inventory.Inactive) 1 else 0)
                put("ModifiedDate", inventory.ModifiedDate?.toString())
                put("Color", inventory.Color)
                put("IconFileName", inventory.IconFileName)
                put("UnitID", inventory.UnitID.toString())
            }

            val result = db.update(
                "Inventory",
                values,
                "InventoryID = ?",
                arrayOf(inventory.InventoryID.toString())
            )

            result > 0
        } catch (ex: Exception) {
            ex.printStackTrace()
            println(ex)
            false
        }
    }

    suspend fun deleteInventory(inventoryID: UUID)
    : Boolean  = withContext(Dispatchers.IO) {

         try {
            val result = db.delete(
                "Inventory",
                "InventoryID = ?",
                arrayOf(inventoryID.toString())
            )
            result > 0
        } catch (ex: Exception) {
            ex.printStackTrace()
            println(ex)
            false
        }
    }

    suspend fun checkInventoryIsInInvoice(inventory: InventoryEntity)
    : Boolean = withContext(Dispatchers.IO) {
        if (inventory.InventoryID == null) false

        val query = """
            SELECT 1 FROM InvoiceDetail i WHERE i.InventoryId = ? LIMIT 1
        """.trimIndent()

        var exists = false
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(query, arrayOf(inventory.InventoryID.toString()))
            exists = cursor.moveToFirst()
        } catch (ex: Exception) {
            ex.printStackTrace()
            println(ex)
        } finally {
            cursor?.close()
        }

        exists
    }
}