package com.example.cukcuk.di

import com.example.presentation.ui.language.LanguageViewModel
import com.example.presentation.shared.SharedViewModel
import com.example.presentation.ui.calculator.CalculatorViewModel
import com.example.presentation.ui.home.HomeViewModel
import com.example.presentation.ui.inventory.inventory_form.InventoryFormViewModel
import com.example.presentation.ui.inventory.inventory_list.InventoryListViewModel
import com.example.presentation.ui.invoice.invoice_form.InvoiceFormViewModel
import com.example.presentation.ui.invoice.invoice_list.InvoiceListViewModel
import com.example.presentation.ui.invoice.invoice_payment.PaymentViewModel
import com.example.presentation.ui.login.login_account.LoginAccountViewModel
import com.example.presentation.ui.product.ProductViewModel
import com.example.presentation.ui.statistic.statistic.StatisticViewModel
import com.example.presentation.ui.statistic.statistic_by_inventory.StatisticByInventoryViewModel
import com.example.presentation.ui.synchronize.SynchronizeViewModel
import com.example.presentation.ui.unit.unit_form.UnitFormViewModel
import com.example.presentation.ui.unit.unit_list.UnitListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { SharedViewModel() }
    viewModel { CalculatorViewModel() }
    viewModel { HomeViewModel(get()) }

    //inventory
    viewModel { InventoryListViewModel(get()) }
    viewModel { InventoryFormViewModel(get(), get(), get(), get(), get()) }

    //invoice
    viewModel { InvoiceFormViewModel(get(), get(), get(), get(), get()) }
    viewModel { InvoiceListViewModel(get(), get()) }
    viewModel { PaymentViewModel(get(), get(), get()) }

    //login
    viewModel { LoginAccountViewModel() }

    //product
    viewModel { ProductViewModel(get()) }

    //statistics
    viewModel { StatisticViewModel(get(), get(), get()) }
    viewModel { StatisticByInventoryViewModel(get()) }

    //synchronize
    viewModel { SynchronizeViewModel(get(), get(), get()) }

    //unit
    viewModel { UnitListViewModel(get(), get(), get()) }
    viewModel { UnitFormViewModel(get(), get(), get()) }

    //language
    viewModel { LanguageViewModel(get()) }
}