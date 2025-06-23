package com.example.presentation.mapper

import android.content.Context
import com.example.domain.enums.DomainError
import com.example.domain.model.Inventory
import com.example.domain.model.ResponseData
import com.example.domain.model.Unit
import com.example.presentation.R


fun <T> ResponseData<T>.getErrorMessage(context: Context): String? {
    return when (error) {
        DomainError.UNKNOWN_ERROR -> context.getString(R.string.ERROR_UNKNOWN)
        DomainError.AUTHENTICATION_ERROR -> context.getString(R.string.ERROR_AUTHENTICATION)
        DomainError.INVENTORY_NAME_BLANK -> context.getString(R.string.ERROR_INVENTORY_NAME_BLANK)
        DomainError.PRICE_LESS_THAN_OR_EQUAL_ZERO -> context.getString(R.string.ERROR_PRICE_LESS_THAN_OR_EQUAL_ZERO)
        DomainError.UNIT_NULL -> context.getString(R.string.ERROR_UNIT_NULL)
        DomainError.INVENTORY_IS_USED_IN_INVOICE -> {
            val inventory = objectData as Inventory
            context.getString(R.string.ERROR_INVENTORY_IS_USED_IN_INVOICE_FIRST) +
                    " ${inventory.InventoryName} " +
                    context.getString(R.string.ERROR_INVENTORY_IS_USED_IN_INVOICE_LAST)
        }
        DomainError.INVOICE_BLANK -> context.getString(R.string.ERROR_INVOICE_BLANK)
        DomainError.UNIT_NAME_EXIST ->{
            val unit = objectData as Unit
            context.getString(R.string.ERROR_UNIT_NAME_EXIST_FIRST) +
                    " ${unit.UnitName.trim()} " +
                    context.getString(R.string.ERROR_UNIT_NAME_EXIST_LAST)
        }
        DomainError.UNIT_IS_USED ->{
            val unit = objectData as Unit
            context.getString(R.string.ERROR_UNIT_IS_USED_FIRST) +
                    " ${unit.UnitName} " +
                    context.getString(R.string.ERROR_UNIT_IS_USED_LAST)
        }

        null -> null
    }
}

