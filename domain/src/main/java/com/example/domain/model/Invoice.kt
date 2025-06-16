package com.example.domain.model

import java.time.LocalDateTime
import java.util.UUID

data class Invoice(
    var InvoiceID: UUID? = null,
    var InvoiceNo: String = "",
    var InvoiceDate: LocalDateTime? = null,
    var Amount: Double = 0.0,
    var ReceiveAmount: Double = 0.0,
    var ReturnAmount: Double = 0.0,
    var PaymentStatus: Int = 0,
    var NumberOfPeople: Int = 0,
    var TableName: String = "",
    var ListItemName: String = "",
    var CreatedDate: LocalDateTime? = null,
    var ModifiedDate: LocalDateTime? = null,
    var InvoiceDetails: MutableList<InvoiceDetail> = mutableListOf<InvoiceDetail>()
)