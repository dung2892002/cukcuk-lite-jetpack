package com.example.cukcuk.presentation.ui.inventory.inventory_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cukcuk.R
import com.example.cukcuk.domain.model.Inventory
import com.example.cukcuk.presentation.components.CukcukImageBox
import com.example.cukcuk.utils.FormatDisplay

@Composable
fun InventoryListScreen(
    navController: NavHostController,
    viewModel: InventoryListViewModel = hiltViewModel()
) {

    val inventories = viewModel.inventories.value

    LaunchedEffect(Unit) {
        viewModel.loadInventories()
    }


    LazyColumn(
        modifier = Modifier
            .background(color = colorResource(R.color.background_color))
    ){
        items(inventories) { inventory ->
            InventoryItem(inventory, onClick = {
                navController.navigate("inventory_form?inventoryId=${inventory.InventoryID}")
            })
        }
    }
}

@Composable
fun InventoryItem(inventory: Inventory, onClick: () -> Unit = {}) {
    val price = FormatDisplay.formatNumber(inventory.Price.toString())
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(R.color.inventory_item_background))
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically

    ) {
        CukcukImageBox(
            color = inventory.Color,
            imageName = inventory.IconFileName
        )

        Column(
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1f)
        ) {
            Text(
                text = inventory.InventoryName,
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "${stringResource(R.string.inventory_price_label)} $price",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(R.color.inventory_price)
                )
                if (!inventory.Inactive) {
                    Text(
                        text = stringResource(R.string.inventory_inactive),
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                color = colorResource(R.color.inventory_item_not_active)
                            )
                            .padding(horizontal = 8.dp),
                    )
                }
            }
        }

        Image(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Preview
@Composable
fun InventoryItemPreview() {
    InventoryItem(
        inventory = Inventory().copy(
            InventoryName = "Món ăn 1",
            Inactive = false,
            Price = 15000.0
        )
    )
}