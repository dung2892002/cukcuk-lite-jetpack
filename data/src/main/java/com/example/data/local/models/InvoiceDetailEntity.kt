package com.example.data.local.models

import java.time.LocalDateTime
import java.util.UUID

data class InvoiceDetailEntity (
    var InvoiceDetailID: UUID? = null,
    var InvoiceDetailType: Int = 0,
    var InvoiceID: UUID? = null,
    var InventoryID: UUID? = null,
    var InventoryName: String = "",
    var UnitID: UUID? = null,
    var UnitName: String = "",
    var Quantity: Double = 0.0,
    var UnitPrice: Double = 0.0,
    var Amount: Double = 0.0,
    var Description: String = "",
    var SortOrder: Int = 0,
    var CreatedDate: LocalDateTime?= null,
    var CreatedBy: String = "",
    var ModifiedDate: LocalDateTime? = null,
    var ModifiedBy: String = ""
)