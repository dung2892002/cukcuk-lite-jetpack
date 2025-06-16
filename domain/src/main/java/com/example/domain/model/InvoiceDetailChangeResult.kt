package com.example.domain.model

data class InvoiceDetailChangeResult(
    val toCreate: MutableList<InvoiceDetail> = mutableListOf(),
    val toUpdate: MutableList<InvoiceDetail> = mutableListOf(),
    val toDelete: MutableList<InvoiceDetail> = mutableListOf(),
    val unchanged: MutableList<InvoiceDetail> = mutableListOf()
)