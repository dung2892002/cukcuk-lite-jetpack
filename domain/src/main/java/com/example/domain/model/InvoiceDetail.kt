package com.example.domain.model

import java.time.LocalDateTime
import java.util.UUID

data class InvoiceDetail(
    var InvoiceDetailID: UUID? = null,
    var InvoiceID: UUID? = null,
    var InventoryID: UUID? = null,
    var InventoryName: String = "",
    var UnitID: UUID? = null,
    var UnitName: String = "",
    var Quantity: Double = 0.0,
    var UnitPrice: Double = 0.0,
    var Amount: Double = 0.0,
    var SortOrder: Int = 0,
    var CreatedDate: LocalDateTime?= null,
    var ModifiedDate: LocalDateTime? = null,
)