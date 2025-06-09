package com.example.cukcuk.presentation.ui.test_api

import androidx.compose.runtime.MutableState
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
    val product = _products

    init {
        fetchProducts()
    }

    fun fetchProducts() {
        viewModelScope.launch {
            _products.value = getProductsUseCase()
        }
    }
}