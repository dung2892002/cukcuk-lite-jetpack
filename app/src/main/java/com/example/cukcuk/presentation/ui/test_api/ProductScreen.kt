package com.example.cukcuk.presentation.ui.test_api

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.cukcuk.domain.model.Product
import com.example.cukcuk.presentation.components.CukcukToolbar

@Composable
fun ProductScreen(
    navController: NavHostController,
    viewModel: ProductViewModel = hiltViewModel()
) {
    val products = viewModel.product.value

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
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            items(products) { product ->
                ProductItem(product)
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
            model = product.image,
            contentDescription = "product image",
            modifier = Modifier
                .width(48.dp)
                .height(60.dp)
        )

        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = product.title,
                maxLines = 2,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = product.category,
                fontStyle = FontStyle.Italic,
                fontSize = 14.sp
            )
        }

        Text(text = product.price.toString())
    }
}