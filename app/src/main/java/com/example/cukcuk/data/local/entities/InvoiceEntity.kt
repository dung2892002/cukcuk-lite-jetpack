package com.example.cukcuk.data.local.entities
import java.time.LocalDateTime
import java.util.UUID

data class InvoiceEntity (
    var InvoiceID: UUID? = null,
    var InvoiceType: Int = 0,
    var InvoiceNo: String = "",
    var InvoiceDate: LocalDateTime? = null,
    var Amount: Double = 0.0,
    var ReceiveAmount: Double = 0.0,
    var ReturnAmount: Double = 0.0,
    var RemainAmount: Double = 0.0,
    var JournalMemo: String = "",
    var PaymentStatus: Int = 0,
    var NumberOfPeople: Int = 0,
    var TableName: String = "",
    var ListItemName: String = "",
    var CreatedDate: LocalDateTime? = null,
    var CreatedBy: String = "",
    var ModifiedDate: LocalDateTime? = null,
    var ModifiedBy: String = "",
)