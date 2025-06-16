package com.example.data.local.mapper

import com.example.data.local.models.InvoiceDetailEntity
import com.example.data.local.models.InvoiceEntity
import com.example.domain.model.Invoice
import com.example.domain.model.InvoiceDetail


fun InvoiceEntity.toDomain(details: List<InvoiceDetailEntity>): Invoice {
    return Invoice(
        InvoiceID = this.InvoiceID,
        Amount = this.Amount,
        ReceiveAmount = this.ReceiveAmount,
        ReturnAmount = this.ReturnAmount,
        PaymentStatus = this.PaymentStatus,
        NumberOfPeople = this.NumberOfPeople,
        TableName = this.TableName,
        ListItemName = this.ListItemName,
        CreatedDate = this.CreatedDate,
        ModifiedDate = this.ModifiedDate,
        InvoiceDetails = details.map { it.toDomain() }.toMutableList()
    )
}

fun InvoiceDetailEntity.toDomain() : InvoiceDetail {
    return InvoiceDetail(
        InvoiceDetailID = this.InvoiceDetailID,
        InvoiceID = this.InvoiceID,
        InventoryID = this.InventoryID,
        InventoryName = this.InventoryName,
        UnitID = this.UnitID,
        UnitName = this.UnitName,
        Quantity = this.Quantity,
        UnitPrice = this.UnitPrice,
        Amount = this.Amount,
        SortOrder = this.SortOrder,
        CreatedDate = this.CreatedDate,
        ModifiedDate = this.ModifiedDate
    )
}

fun Invoice.toEntity() : InvoiceEntity {
    return InvoiceEntity(
        InvoiceID = this.InvoiceID,
        Amount = this.Amount,
        InvoiceDate = this.InvoiceDate,
        ReceiveAmount = this.ReceiveAmount,
        ReturnAmount = this.ReturnAmount,
        PaymentStatus = this.PaymentStatus,
        NumberOfPeople = this.NumberOfPeople,
        TableName = this.TableName,
        ListItemName = this.ListItemName,
        CreatedDate = this.CreatedDate,
        ModifiedDate = this.ModifiedDate,
    )
}

fun InvoiceDetail.toEntity() : InvoiceDetailEntity {
    return InvoiceDetailEntity(
        InvoiceDetailID = this.InvoiceDetailID,
        InvoiceID = this.InvoiceID,
        InventoryID = this.InventoryID,
        InventoryName = this.InventoryName,
        UnitID = this.UnitID,
        UnitName = this.UnitName,
        Quantity = this.Quantity,
        UnitPrice = this.UnitPrice,
        Amount = this.Amount,
        SortOrder = this.SortOrder,
        CreatedDate = this.CreatedDate,
        ModifiedDate = this.ModifiedDate
    )
}