package com.example.cukcuk.presentation.ui.product

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cukcuk.domain.model.Product
import com.example.cukcuk.domain.usecase.product.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _products: MutableState<List<Product>> = mutableStateOf(emptyList())
    val products: State<List<Product>>  = _products

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    init {
        fetchProducts()
    }

    fun fetchProducts() {
        viewModelScope.launch {
            try {
                _loading.value = true
                val response = getProductsUseCase()
                if (response.isSuccess) {
                    _products.value = response.data ?: emptyList()
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = response.message ?: "Unknown error"
                }
            } catch (e: Exception) {
                println(e)
            } finally {
                _loading.value = false
            }
        }
    }
 }