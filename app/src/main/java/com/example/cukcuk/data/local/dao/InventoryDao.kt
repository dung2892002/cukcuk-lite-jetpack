package com.example.cukcuk.data.local.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.cukcuk.domain.model.Inventory
import com.example.cukcuk.utils.getBoolean
import com.example.cukcuk.utils.getDouble
import com.example.cukcuk.utils.getString
import com.example.cukcuk.utils.getUUID
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class InventoryDao @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val db: SQLiteDatabase by lazy {
        context.openOrCreateDatabase("cukcuk.db", Context.MODE_PRIVATE, null)
    }

    suspend fun getAllInventory() : List<Inventory>  = withContext(Dispatchers.IO) {
        val query =""" 
            SELECT 
                i.InventoryID, i.InventoryName, i.Price,
                i.Inactive, i.Color, i.IconFileName,
                u.UnitId, u.UnitName
            FROM 
                Inventory i
                JOIN Unit u ON i.UnitID = u.UnitId
        """.trimIndent()
        val inventoryList = mutableListOf<Inventory>()
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(query,null)
            while (cursor.moveToNext()) {
                val inventory = Inventory(
                    InventoryID = cursor.getUUID("InventoryID"),
                    InventoryCode = "",
                    InventoryName = cursor.getString("InventoryName"),
                    InventoryType = 0,
                    Price = cursor.getDouble("Price"),
                    Description = "",
                    Inactive = cursor.getBoolean("Inactive"),
                    CreatedBy = "",
                    ModifiedBy = "",
                    CreatedDate = null,
                    ModifiedDate = null,
                    Color = cursor.getString("Color"),
                    IconFileName = cursor.getString("IconFileName"),
                    UseCount = 0,
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

    suspend fun getInventoryById(inventoryID: UUID) : Inventory? = withContext(Dispatchers.IO) {
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

        var inventory: Inventory? = null
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(query, arrayOf(inventoryID.toString()))
            if (cursor.moveToFirst()) {
                inventory = Inventory(
                    InventoryID = cursor.getUUID("InventoryID"),
                    InventoryCode = "",
                    InventoryName = cursor.getString("InventoryName"),
                    InventoryType = 0,
                    Price = cursor.getDouble("Price"),
                    Description = "",
                    Inactive = cursor.getBoolean("Inactive"),
                    CreatedBy = "",
                    ModifiedBy = "",
                    CreatedDate = null,
                    ModifiedDate = null,
                    Color = cursor.getString("Color"),
                    IconFileName = cursor.getString("IconFileName"),
                    UseCount = 0,
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

    suspend fun createInventory(inventory: Inventory): Boolean = withContext(Dispatchers.IO) {
        try {
            val values = ContentValues().apply {
                put("InventoryID", inventory.InventoryID?.toString() ?: UUID.randomUUID().toString())
                put("InventoryCode", inventory.InventoryCode)
                put("InventoryName", inventory.InventoryName)
                put("InventoryType", inventory.InventoryType)
                put("Price", inventory.Price)
                put("Description", inventory.Description)
                put("Inactive", if (inventory.Inactive) 1 else 0)
                put("CreatedBy", inventory.CreatedBy)
                put("ModifiedBy", inventory.ModifiedBy)
                put("CreatedDate", inventory.CreatedDate?.toString())
                put("ModifiedDate", inventory.ModifiedDate?.toString())
                put("Color", inventory.Color)
                put("IconFileName", inventory.IconFileName)
                put("UseCount", inventory.UseCount)
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

    suspend fun updateInventory(inventory: Inventory): Boolean = withContext(Dispatchers.IO) {
        if (inventory.InventoryID == null) false

        try {
            val values = ContentValues().apply {
                put("InventoryCode", inventory.InventoryCode)
                put("InventoryName", inventory.InventoryName)
                put("InventoryType", inventory.InventoryType)
                put("Price", inventory.Price)
                put("Description", inventory.Description)
                put("Inactive", if (inventory.Inactive) 1 else 0)
                put("ModifiedBy", inventory.ModifiedBy)
                put("ModifiedDate", inventory.ModifiedDate?.toString())
                put("Color", inventory.Color)
                put("IconFileName", inventory.IconFileName)
                put("UseCount", inventory.UseCount)
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

    suspend fun deleteInventory(inventoryID: UUID): Boolean  = withContext(Dispatchers.IO) {

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

    suspend fun checkInventoryIsInInvoice(inventory: Inventory): Boolean = withContext(Dispatchers.IO) {
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