package com.example.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.presentation.shared.SharedViewModel
import com.example.presentation.ui.app_info.AppInfoScreen
import com.example.presentation.ui.feedback.FeedbackScreen
import com.example.presentation.ui.home.HomeScreen
import com.example.presentation.ui.inventory.inventory_form.InventoryFormScreen
import com.example.presentation.ui.invoice.invoice_form.InvoiceFormScreen
import com.example.presentation.ui.invoice.invoice_payment.PaymentScreen
import com.example.presentation.ui.language.LanguageScreen
import com.example.presentation.ui.link_account.LinkAccountScreen
import com.example.presentation.ui.login.LoginScreen
import com.example.presentation.ui.login.login_account.LoginAccountScreen
import com.example.presentation.ui.login.register.RegisterScreen
import com.example.presentation.ui.notification.NotificationScreen
import com.example.presentation.ui.set_password.SetPasswordScreen
import com.example.presentation.ui.setting.SettingScreen
import com.example.presentation.ui.splash.SplashScreen
import com.example.presentation.ui.statistic.statistic_by_inventory.StatisticByInventoryScreen
import com.example.presentation.ui.synchronize.SynchronizeScreen
import com.example.presentation.ui.product.ProductScreen
import com.example.presentation.ui.unit.unit_list.UnitListScreen
import org.koin.androidx.compose.koinViewModel


@Composable
fun AppNavHost(navController: NavHostController) {
    val sharedViewModel: SharedViewModel = koinViewModel()

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

        composable(
            route = "product_api"
        ) {
            ProductScreen(navController)
        }

        composable(
            route = "language"
        ) {
            LanguageScreen(navController)
        }
    }
}
