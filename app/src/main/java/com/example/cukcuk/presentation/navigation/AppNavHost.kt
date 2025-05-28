package com.example.cukcuk.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cukcuk.presentation.ui.home.HomeScreen
import com.example.cukcuk.presentation.ui.inventory.inventory_form.InventoryFormScreen
import com.example.cukcuk.presentation.ui.inventory.inventory_list.InventoryListScreen
import com.example.cukcuk.presentation.ui.invoice.invoice_form.InvoiceFormScreen
import com.example.cukcuk.presentation.ui.unit.unit_list.UnitListScreen


@Composable
fun AppNavHost(navController: NavHostController,) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
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
            InventoryFormScreen(navController, backStackEntry)
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
            InvoiceFormScreen(navController, backStackEntry)
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
            UnitListScreen(navController, backStackEntry)
        }
    }
}
