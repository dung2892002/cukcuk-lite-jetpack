package com.example.presentation.ui.login.login_account

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.presentation.R
import com.example.presentation.components.CukcukButton
import com.example.presentation.components.CukcukDialog
import com.example.presentation.components.CukcukToolbar
import com.example.presentation.utils.SharedPrefManager


@Composable
fun LoginAccountScreen(
    navController: NavHostController,
    viewModel: LoginAccountViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val account = viewModel.account.value
    val password = viewModel.password.value
    val showDialogForgetPassword = viewModel.showDialogForgetPassword.value
    val accountForgetPassword = viewModel.accountForgetPassword.value

    fun login() {
        val response = viewModel.handleLogin()
        if (response.isSuccess) {
            SharedPrefManager.setLoggedIn(context, true)
            navController.navigate("home") {
                popUpTo(0) {inclusive = true}
                launchSingleTop = true
            }
        }
        else {
            Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            CukcukToolbar(
                title = "",
                menuTitle = null,
                onBackClick = {
                    navController.popBackStack()
                },
                onMenuClick = {}
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(
                    color = colorResource(R.color.main_color)
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Image(
                contentDescription = null,
                painter = painterResource(R.drawable.app_icon_white)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        colorResource(R.color.white)
                    )
            ) {
                InputFieldWithAccount(
                    iconResId = R.drawable.user_icon,
                    value = account,
                    onValueChange = { viewModel.updateAccount(it) },
                    placeholder = "Số điện thoại hoặc email"
                )

                InputFieldWithAccount(
                    iconResId = R.drawable.lock_icon,
                    value = password,
                    onValueChange = { viewModel.updatePassword(it) },
                    placeholder = "Mật khẩu",
                    isPassword = true
                )

            }

            CukcukButton(
                title = "ĐĂNG NHẬP",
                onClick = {
                    login()
                },
                bgColor = colorResource(R.color.main_color_bold),
                textColor = colorResource(R.color.white),
                fontSize = 20,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Quên mật khẩu?",
                color = colorResource(R.color.white),
                modifier = Modifier.clickable{
                    viewModel.openDialogForgetPassword()
                }
            )

            Spacer(modifier = Modifier.weight(1f))
            Icon(
                contentDescription = null,
                painter = painterResource(R.drawable.ic_nav_app_info),
                tint = colorResource(R.color.white),
                modifier = Modifier.size(24.dp)
            )
        }
    }

    if(showDialogForgetPassword) {
        CukcukDialog(
            title = "Quên mật khẩu",
            message = buildAnnotatedString {
                append("Nhập số điện thoại hoặc email mà bạn đã đăng ký ử dng, chúng tôi sẽ gửi hướng dẫn lấy lại mật khẩu.")
            },
            placeHolderValue = "Nhập số điện thoại hoặc email",
            valueTextField = accountForgetPassword,
            colorBorderTextField = Color.Gray,
            onValueChange = {
                viewModel.updateAccountForgetPassword(it)
            },
            onCancel = {
                viewModel.closeDialogForgetPassword()
            },
            onConfirm = {
                viewModel.handleForgotPassword()
            },
            confirmButtonText = "TIẾP TỤC",
            buttonTextSize = 12
        )
    }
}

