package com.example.cukcuk.domain.dtos

import com.example.cukcuk.domain.model.InvoiceDetail

data class InvoiceDetailChangeResult(
    val toCreate: MutableList<InvoiceDetail> = mutableListOf(),
    val toUpdate: MutableList<InvoiceDetail> = mutableListOf(),
    val toDelete: MutableList<InvoiceDetail> = mutableListOf(),
    val unchanged: MutableList<InvoiceDetail> = mutableListOf()
)