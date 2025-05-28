package com.example.cukcuk.presentation.ui.inventory.inventory_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cukcuk.R
import com.example.cukcuk.domain.model.Inventory
import com.example.cukcuk.utils.FormatDisplay
import com.example.cukcuk.utils.ImageHelper
import com.example.cukcuk.utils.ImageHelper.loadImageFromAssets

@Composable
fun InventoryListScreen(
    navController: NavHostController,
    viewModel: InventoryListViewModel = hiltViewModel()
) {

    val inventories = viewModel.inventories.value

    LazyColumn{
        items(inventories) { inventory ->
            InventoryItem(inventory, onClick = {
                navController.navigate("inventory_form?inventoryId=${inventory.InventoryID}")
            })
        }
    }
}

@Composable
fun InventoryItem(inventory: Inventory, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically

    ) {
        Card(
            modifier = Modifier.size(48.dp),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(contentColor = ImageHelper.parseColor(inventory.Color))
        ) {
            val imageBitmap: ImageBitmap? = loadImageFromAssets(inventory.IconFileName)
            imageBitmap?.let {
                Image(
                    bitmap = it,
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1f)
        ) {
            Text(
                text = inventory.InventoryName,
                style = MaterialTheme.typography.titleMedium
            )

            Row {
                Text(
                    text = FormatDisplay.formatNumber(inventory.Price.toString()),
                    style = MaterialTheme.typography.bodyMedium
                )
                if (!inventory.Inactive) {
                    Text(
                        text = "Ngừng bán",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }

        Image(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = "Ảnh ví dụ",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(20.dp)
        )
    }
}