package com.example.cukcuk.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cukcuk.presentation.shared.SharedViewModel
import com.example.cukcuk.presentation.ui.home.HomeScreen
import com.example.cukcuk.presentation.ui.inventory.inventory_form.InventoryFormScreen
import com.example.cukcuk.presentation.ui.invoice.invoice_form.InvoiceFormScreen
import com.example.cukcuk.presentation.ui.invoice.invoice_payment.PaymentScreen
import com.example.cukcuk.presentation.ui.statistic.statistic_by_inventory.StatisticByInventoryScreen
import com.example.cukcuk.presentation.ui.unit.unit_list.UnitListScreen


@Composable
fun AppNavHost(navController: NavHostController) {
    val sharedViewModel: SharedViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController, sharedViewModel)
        }

        composable(
            route = "inventory_form?inventoryId={inventoryId}",
            arguments = listOf(
                navArgument("inventoryId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            InventoryFormScreen(navController)
        }

        composable(
            route = "invoice_form?invoiceId={invoiceId}",
            arguments = listOf(
                navArgument("invoiceId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            InvoiceFormScreen(navController)
        }

        composable(
            route = "unit_list?currentUnitId={currentUnitId}",
            arguments = listOf(
                navArgument("currentUnitId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            UnitListScreen(navController)
        }

        composable(
            route = "payment?invoiceId={invoiceId}",
            arguments = listOf(
                navArgument("invoiceId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            PaymentScreen(navController,)
        }

        composable (
            route = "statistic_by_inventory"
        ) {
            StatisticByInventoryScreen(navController, sharedViewModel)
        }
    }
}
