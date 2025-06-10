package com.example.cukcuk.presentation.ui.login.login_account

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cukcuk.domain.common.ResponseData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginAccountViewModel @Inject constructor(

) : ViewModel() {
    private val _account = mutableStateOf("")
    val account: State<String> = _account

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _showDialogForgetPassword = mutableStateOf(false)
    val showDialogForgetPassword: State<Boolean> = _showDialogForgetPassword

    private val _accountForgetPassword = mutableStateOf("")
    val accountForgetPassword: State<String> = _accountForgetPassword

    fun updateAccount(account: String) {
        _account.value = account
    }

    fun updatePassword(password: String) {
        _password.value = password
    }

    fun updateAccountForgetPassword(account: String) {
        _accountForgetPassword.value = account
    }

    fun openDialogForgetPassword() {
        _showDialogForgetPassword.value = true
    }

    fun closeDialogForgetPassword() {
        _showDialogForgetPassword.value = false
    }

    fun handleLogin() : ResponseData{
        val response = ResponseData(false, "Tài khoản hoặc mật khẩu không đúng")
        if (_account.value == "dung2002" && _password.value == "Dung2002*")
            response.isSuccess = true
        return response
    }

    fun handleForgotPassword() {
        closeDialogForgetPassword()
    }
}