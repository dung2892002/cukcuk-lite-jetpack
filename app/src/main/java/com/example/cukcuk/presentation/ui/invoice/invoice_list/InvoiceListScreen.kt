package com.example.cukcuk.presentation.ui.invoice.invoice_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cukcuk.domain.model.Invoice

@Composable
fun InvoiceListScreen(
    navController: NavHostController,
    viewModel: InvoiceListViewModel = hiltViewModel()
) {
    val invoices = viewModel.invoices.value

    LaunchedEffect(Unit) {
        viewModel.loadInvoiceNotPayment()
    }
}

@Composable
fun InvoiceItem(
    invoice: Invoice,
    onDeleteClick: () -> Unit,
    onPaymentClick: () -> Unit
) {

}