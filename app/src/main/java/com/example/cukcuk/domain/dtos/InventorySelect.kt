package com.example.cukcuk.domain.dtos


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import com.example.cukcuk.domain.model.Inventory
import java.io.Serializable

data class InventorySelect(
    val inventory: Inventory = Inventory(),
    val quantity: MutableState<Double> = mutableDoubleStateOf(0.0)
) : Serializable