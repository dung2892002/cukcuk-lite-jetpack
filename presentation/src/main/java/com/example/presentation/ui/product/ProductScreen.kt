package com.example.presentation.ui.product

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.presentation.R
import com.example.domain.model.Product
import com.example.presentation.components.CukcukLoadingDialog
import com.example.presentation.components.CukcukToolbar
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductScreen(
    navController: NavHostController,
    viewModel: ProductViewModel = koinViewModel()
) {
    val products = viewModel.products.value
    val loading = viewModel.loading.value
    val errorMessage = viewModel.errorMessage.value

    Scaffold(
        topBar = {
            CukcukToolbar(
                title = "product",
                menuTitle = null,
                hasMenuIcon = false,
                onBackClick = {
                    navController.popBackStack()
                },
                onMenuClick = {}
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(color = colorResource(R.color.white))
        ){
            LazyColumn{
                items(products) { product ->
                    ProductItem(product)
                }
            }
            if (errorMessage != null) {
                Text(
                    text = errorMessage,
                    color = colorResource(R.color.error_message),
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center)
                )
            }
            if (loading) {
                CukcukLoadingDialog(
                    title = "Loading products...",
                )
            }
        }
    }
}


@Composable
fun ProductItem(
    product: Product
) {
    Row(
        modifier = Modifier
            .padding(8.dp),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            model = product.Image,
            contentDescription = "product image",
            modifier = Modifier
                .width(48.dp)
                .height(60.dp)
        )

        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = product.Title,
                maxLines = 2,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = product.Category,
                fontStyle = FontStyle.Italic,
                fontSize = 14.sp
            )
        }

        Text(text = product.Price.toString())
    }
}