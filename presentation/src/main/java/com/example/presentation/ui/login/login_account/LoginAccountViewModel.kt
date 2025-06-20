package com.example.presentation.ui.login.login_account

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.domain.enums.DomainError
import com.example.domain.model.ResponseData

class LoginAccountViewModel(
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

    fun handleLogin() : ResponseData<String>{
        val response = ResponseData<String>(false, DomainError.AUTHENTICATION_ERROR)
        if (_account.value == "dung2002" && _password.value == "Dung2002*")
            response.isSuccess = true
        return response
    }

    fun handleForgotPassword() {
        closeDialogForgetPassword()
    }
}