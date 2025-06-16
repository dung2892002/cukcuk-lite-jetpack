package com.example.data.local.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.data.local.getString
import com.example.data.local.getUUID
import com.example.data.local.models.UnitEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class UnitDao (
    private val context: Context
) {
    private val db: SQLiteDatabase by lazy {
        context.openOrCreateDatabase("cukcuk.db", Context.MODE_PRIVATE, null)
    }

    suspend fun checkUseByInventory(unitId: UUID)
    : Boolean = withContext(Dispatchers.IO) {
        val query = """
            SELECT 1 FROM Inventory i WHERE i.UnitID = ? LIMIT 1
        """.trimIndent()

        var exists = false
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(query, arrayOf(unitId.toString()))
            exists = cursor.moveToFirst()
        } catch (ex: Exception) {
            ex.printStackTrace()
            println(ex)
        } finally {
            cursor?.close()
        }

        exists
    }

    suspend fun checkExistUnitName(name: String, unitId: UUID?)
    : Boolean = withContext(Dispatchers.IO) {
        val query: String
        val args: Array<String>

        if (unitId == null) {
            query = """
            SELECT 1 FROM Unit u
            WHERE u.UnitName = ?
            LIMIT 1
        """.trimIndent()
            args = arrayOf(name)
        } else {
            query = """
            SELECT 1 FROM Unit u
            WHERE u.UnitName = ? AND u.UnitID != ?
            LIMIT 1
        """.trimIndent()
            args = arrayOf(name, unitId.toString())
        }

        var exists = false
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(query, args)
            exists = cursor.moveToFirst()
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            cursor?.close()
        }

        exists
    }

    suspend fun getAllUnit()
    : List<UnitEntity> = withContext(Dispatchers.IO) {
        val units = mutableListOf<UnitEntity>()

        val query = """
            SELECT u.UnitID, u.UnitName FROM Unit u
        """.trimIndent()
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(query, null)
            while (cursor.moveToNext()) {
                val unit = UnitEntity(
                    UnitID = cursor.getUUID("UnitID"),
                    UnitName = cursor.getString("UnitName")
                )
                units.add(unit)
            }
        }
        catch (ex: Exception) {
            ex.printStackTrace()
            println(ex)
        }
        finally {
            cursor?.close()
        }
        units.toList()
    }

    suspend fun getUnitById(unitId: UUID)
    : UnitEntity? = withContext(Dispatchers.IO) {
        var unit : UnitEntity? = null

        val query = """
            SELECT u.UnitID, u.UnitName FROM Unit u
            WHERE UnitID = ?
        """.trimIndent()
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(query, arrayOf(unitId.toString()))
            if (cursor.moveToFirst()) {
                unit = UnitEntity(
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
        unit
    }

    suspend fun createUnit(unit: UnitEntity)
    : Boolean = withContext(Dispatchers.IO) {
        try {
            val values = ContentValues().apply {
                put("UnitID", unit.UnitID.toString())
                put("UnitName", unit.UnitName)
                put("CreatedDate", unit.CreatedDate.toString())
            }

            val result = db.insert("Unit", null, values)
            result != -1L
        }
        catch (ex: Exception) {
            ex.printStackTrace()
            println(ex)
            false
        }
    }

    suspend fun updateUnit(unit: UnitEntity)
    : Boolean = withContext(Dispatchers.IO) {
        try {
            val values = ContentValues().apply {
                put("UnitName", unit.UnitName)
                put("ModifiedDate", unit.ModifiedDate.toString())
            }

            val result = db.update(
                "Unit",
                values,
                "UnitID = ?",
                arrayOf(unit.UnitID.toString())
            )
            result > 0
        }
        catch (ex: Exception) {
            ex.printStackTrace()
            println(ex)
            false
        }
    }

    suspend fun deleteUnit(unitId: UUID)
    : Boolean = withContext(Dispatchers.IO) {
        try {
            val result = db.delete(
                "Unit",
                "UnitID = ?",
                arrayOf(unitId.toString())
            )
            result > 0
        } catch (ex: Exception) {
            ex.printStackTrace()
            println(ex)
            false
        }
    }
}