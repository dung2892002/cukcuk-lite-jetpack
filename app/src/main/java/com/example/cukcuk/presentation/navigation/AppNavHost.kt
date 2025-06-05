package com.example.cukcuk.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cukcuk.presentation.shared.SharedViewModel
import com.example.cukcuk.presentation.ui.app_info.AppInfoScreen
import com.example.cukcuk.presentation.ui.feedback.FeedbackScreen
import com.example.cukcuk.presentation.ui.home.HomeScreen
import com.example.cukcuk.presentation.ui.inventory.inventory_form.InventoryFormScreen
import com.example.cukcuk.presentation.ui.invoice.invoice_form.InvoiceFormScreen
import com.example.cukcuk.presentation.ui.invoice.invoice_payment.PaymentScreen
import com.example.cukcuk.presentation.ui.link_account.LinkAccountScreen
import com.example.cukcuk.presentation.ui.login.LoginScreen
import com.example.cukcuk.presentation.ui.login.login_account.LoginAccountScreen
import com.example.cukcuk.presentation.ui.login.register.RegisterScreen
import com.example.cukcuk.presentation.ui.notification.NotificationScreen
import com.example.cukcuk.presentation.ui.set_password.SetPasswordScreen
import com.example.cukcuk.presentation.ui.setting.SettingScreen
import com.example.cukcuk.presentation.ui.splash.SplashScreen
import com.example.cukcuk.presentation.ui.statistic.statistic_by_inventory.StatisticByInventoryScreen
import com.example.cukcuk.presentation.ui.synchronize.SynchronizeScreen
import com.example.cukcuk.presentation.ui.unit.unit_list.UnitListScreen


@Composable
fun AppNavHost(navController: NavHostController) {
    val sharedViewModel: SharedViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") {
            SplashScreen(navController)
        }

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
            route = "payment?invoiceId={invoiceId}",
            arguments = listOf(
                navArgument("invoiceId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            PaymentScreen(navController)
        }

        composable (
            route = "statistic_by_inventory"
        ) {
            StatisticByInventoryScreen(navController, sharedViewModel)
        }

        composable(
            route = "synchronize"
        ) {
            SynchronizeScreen(navController)
        }

        composable(
            route = "setting"
        ) {
            SettingScreen(navController)
        }

        composable(
            route = "link_account"
        ) {
            LinkAccountScreen(navController)
        }

        composable(
            route = "notification"
        ) {
            NotificationScreen(navController)
        }

        composable(
            route = "feedback"
        ) {
            FeedbackScreen(navController)
        }

        composable(
            route = "app_info"
        ) {
            AppInfoScreen(navController)
        }

        composable(
            route = "set_password"
        ) {
            SetPasswordScreen(navController)
        }

        composable(
            route = "login"
        ) {
            LoginScreen(navController)
        }

        composable(
            route = "login_account"
        ) {
            LoginAccountScreen(navController)
        }

        composable(
            route = "register"
        ) {
            RegisterScreen(navController)
        }
    }
}
