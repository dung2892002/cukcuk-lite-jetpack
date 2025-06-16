package com.example.domain.model

data class StatisticByInventory (
    var InventoryName: String = "",
    var Quantity: Double = 0.0,
    var Amount: Double = 0.0,
    var UnitName: String = "",
    var Percentage: Double = 0.0,
    var SortOrder: Int = 0,
    var Color: String = ""
)