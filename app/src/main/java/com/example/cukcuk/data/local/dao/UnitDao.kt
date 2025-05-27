package com.example.cukcuk.data.local.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.cukcuk.domain.model.Unit
import com.example.cukcuk.utils.getBoolean
import com.example.cukcuk.utils.getDateTime
import com.example.cukcuk.utils.getString
import com.example.cukcuk.utils.getUUID
import java.util.UUID
import javax.inject.Inject

class UnitDao @Inject constructor(
    private val context: Context
) {
    private val db: SQLiteDatabase by lazy {
        context.openOrCreateDatabase("cukcuk.db", Context.MODE_PRIVATE, null)
    }

    fun checkUseByInventory(unitId: UUID) : Boolean {
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

        return exists
    }

    fun checkExistUnitName(name: String, unitId: UUID?): Boolean {
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

        return exists
    }

    fun getAllUnit() : List<Unit> {
        val units = mutableListOf<Unit>()

        val query = """
            SELECT u.UnitID, u.UnitName, u.Inactive FROM Unit u
        """.trimIndent()
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(query, null)
            while (cursor.moveToNext()) {
                val unit = Unit(
                    UnitID = cursor.getUUID("UnitID"),
                    UnitName = cursor.getString("UnitName"),
                    Description = "",
                    Inactive = cursor.getBoolean("Inactive"),
                    CreatedBy = "",
                    ModifiedBy = "",
                    CreatedDate = null,
                    ModifiedDate = null
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
        return units.toList()
    }

    fun getUnitById(unitId: UUID) : Unit? {
        var unit : Unit? = null

        val query = """
            SELECT * FROM Unit u
            WHERE UnitID = ?
        """.trimIndent()
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(query, arrayOf(unitId.toString()))
            if (cursor.moveToFirst()) {
                unit = Unit(
                    UnitID = cursor.getUUID("UnitID"),
                    UnitName = cursor.getString("UnitName"),
                    Description = cursor.getString("Description"),
                    Inactive = cursor.getBoolean("Inactive"),
                    CreatedBy = cursor.getString("CreatedBy"),
                    ModifiedBy = cursor.getString("ModifiedBy"),
                    CreatedDate = cursor.getDateTime("CreatedDate"),
                    ModifiedDate = cursor.getDateTime("ModifiedDate"),
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
        return unit
    }

    fun createUnit(unit: Unit): Boolean {
        return try {
            val values = ContentValues().apply {
                put("UnitID", unit.UnitID?.toString() ?: UUID.randomUUID().toString())
                put("UnitName", unit.UnitName)
                put("Description", unit.Description)
                put("Inactive", if (unit.Inactive) 1 else 0)
                put("CreatedBy", unit.CreatedBy)
                put("ModifiedBy", unit.ModifiedBy)
                put("CreatedDate", unit.CreatedDate.toString())
                put("ModifiedDate", unit.ModifiedDate.toString())
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

    fun updateUnit(unit: Unit): Boolean {
        if (unit.UnitID == null) return false

        return try {
            val values = ContentValues().apply {
                put("UnitName", unit.UnitName)
                put("Description", unit.Description)
                put("Inactive", if (unit.Inactive) 1 else 0)
                put("ModifiedBy", unit.ModifiedBy)
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

    fun deleteUnit(unitId: UUID) : Boolean {
        return try {
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