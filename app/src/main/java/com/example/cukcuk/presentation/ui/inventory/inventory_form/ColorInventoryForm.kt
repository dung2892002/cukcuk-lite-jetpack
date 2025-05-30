package com.example.cukcuk.presentation.ui.inventory.inventory_form

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cukcuk.presentation.enums.ColorInventory
import com.example.cukcuk.R
import com.example.cukcuk.presentation.components.CukcukImageBox
import com.example.cukcuk.presentation.theme.CukcukTheme


@Composable
fun ColorInventoryForm(
    currentColor: String,
    onSelectColor: (ColorInventory) -> Unit,
    onCloseForm: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f))
            .clickable(enabled = false) {}
            .padding(horizontal = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentSize()
                .background(
                    color = colorResource(R.color.background_form_select_image_inventory),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(vertical = 10.dp)
        ) {
            ColorInventoryGrid(
                currentColor = currentColor,
                onColorSelected = {
                    onSelectColor(it)
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp)
            ) {
                Text(
                    text = "Hủy bỏ",
                    textAlign = TextAlign.Right,
                    color = colorResource(R.color.main_color),
                    fontSize = 24.sp,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable {
                            onCloseForm()
                        }
                )
            }
        }
    }
}

@Composable
fun ColorInventoryGrid(
    currentColor: String,
    onColorSelected: (ColorInventory) -> Unit) {
    val colors = ColorInventory.entries.toTypedArray()

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier
            .wrapContentSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        items(colors.size) { index ->
            val colorItem = colors[index]

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CukcukImageBox(
                    color = colorItem.value,
                    size = 48,
                    imageSize = 36,
                    onClick = {
                        onColorSelected(colorItem)
                    },
                    iconDrawable = if (currentColor == colorItem.value) painterResource(R.drawable.ic_yes) else null
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewColorInventoryForm(){
    CukcukTheme {
        ColorInventoryForm(
            currentColor = ColorInventory.COLOR_1.value,
            onSelectColor = {},
            onCloseForm = {}
        )
    }
}