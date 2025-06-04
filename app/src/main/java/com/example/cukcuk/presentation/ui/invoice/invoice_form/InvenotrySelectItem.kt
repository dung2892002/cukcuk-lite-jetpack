package com.example.cukcuk.presentation.ui.invoice.invoice_form

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cukcuk.R
import com.example.cukcuk.domain.dtos.InventorySelect
import com.example.cukcuk.domain.model.Inventory
import com.example.cukcuk.presentation.components.CukcukButton
import com.example.cukcuk.presentation.components.CukcukImageBox
import com.example.cukcuk.presentation.theme.CukcukTheme
import com.example.cukcuk.utils.FormatDisplay

@Composable
fun InventorySelectItem(
    item: InventorySelect,
    onItemClick: () -> Unit,
    onButtonAddClick: () -> Unit,
    onButtonSubtractClick: () -> Unit,
    onButtonRemoveClick: () -> Unit,
    onCalculatorClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (item.quantity.value > 0) colorResource(R.color.inventory_selected) else Color.White
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable{
                    onItemClick()
                }
                .drawBehind{
                    val strokeWidth = 1.dp.toPx()
                    val xStart = 0f
                    val xEnd = size.width
                    val y = size.height

                    drawLine(
                        color = Color.LightGray,
                        start = Offset(xStart, y),
                        end = Offset(xEnd, y),
                        strokeWidth = strokeWidth
                    )
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.padding(10.dp)
            ) {
                if (item.quantity.value == 0.0) {
                    CukcukImageBox(
                        color = item.inventory.Color,
                        imageName = item.inventory.IconFileName,
                        onClick = {
                            onItemClick()
                        },
                        size = 48,
                        imageSize = 36
                    )
                }
                else {
                    CukcukImageBox(
                        colorRes = colorResource(R.color.inventory_selected_icon),
                        iconDrawable = painterResource(R.drawable.ic_yes),
                        imageSize = 24,
                        onClick = {
                            onButtonRemoveClick()
                        }
                    )
                }
            }

            Column {
                Text(
                    text = item.inventory.InventoryName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = FormatDisplay.formatNumber(item.inventory.Price.toString()),
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            }
        }

        if (item.quantity.value > 0) {
            Row(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        colorResource(R.color.inventory_selected)
                    )
                    .align(Alignment.CenterEnd),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically,

            ) {
                CukcukImageBox(
                    colorRes = Color.White,
                    iconDrawable = painterResource(R.drawable.ic_subtract),
                    tintIcon = Color.Red,
                    size = 40,
                    imageSize = 32,
                    onClick = {
                        onButtonSubtractClick()
                    }
                )

                CukcukButton(
                    title = FormatDisplay.formatNumber(item.quantity.value.toString()),
                    bgColor = Color.White,
                    textColor = Color.Black,
                    onClick = {
                        onCalculatorClick()
                    },
                    padding = 2,
                    modifier = Modifier
                        .height(40.dp)
                        .widthIn(min = 40.dp, max = 120.dp)
                )

                CukcukImageBox(
                    colorRes = Color.White,
                    iconDrawable = painterResource(R.drawable.icon_add),
                    tintIcon = colorResource(R.color.inventory_selected_icon),
                    size = 40,
                    imageSize = 32,
                    onClick = {
                        onButtonAddClick()
                    }
                )
            }
        }
    }
}



@Preview
@Composable
fun InventorySelectItemPreview() {
    CukcukTheme {
        InventorySelectItem(
            item = InventorySelect(
                quantity = remember { mutableDoubleStateOf(0.0) } ,
                inventory = Inventory(
                    InventoryName = "Bún đậu đầy đủ không dồi",
                    Color = "#FF0000",
                    Price = 45000.0
                )
            ),
            onItemClick = {},
            onButtonAddClick = {},
            onButtonSubtractClick = {},
            onButtonRemoveClick = {},
            onCalculatorClick = {}
        )
    }
}