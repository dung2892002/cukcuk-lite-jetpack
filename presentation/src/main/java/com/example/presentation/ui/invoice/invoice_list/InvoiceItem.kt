package com.example.presentation.ui.invoice.invoice_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.R
import com.example.domain.model.Invoice
import com.example.presentation.components.CukcukTextBox
import com.example.presentation.theme.CukcukTheme
import com.example.domain.utils.FormatDisplay

@Composable
fun InvoiceItem(
    invoice: Invoice,
    onItemClicked: () -> Unit = {},
    onDeleteClick: () -> Unit,
    onPaymentClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(
                RoundedCornerShape(topStart = 2.dp, bottomStart = 2.dp)
            )
            .background(
                color = colorResource(R.color.white)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    val yStart = 0f
                    val yEnd = size.height
                    val x = size.width - strokeWidth / 2
                    drawLine(
                        color = Color.Gray,
                        start = Offset(x, yStart),
                        end = Offset(x, yEnd),
                        strokeWidth = strokeWidth
                    )
                }
                .background(
                    color = Color.Transparent
                )
                .clickable{
                    onItemClicked()
                }
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CukcukTextBox(
                colorRes =
                    if (invoice.TableName.isEmpty())
                        colorResource(R.color.invoice_item_color)
                    else
                        colorResource(R.color.invoice_table_block_exist),
                textValue = invoice.TableName,
                size = 64
            )
            if (invoice.NumberOfPeople != 0) {
                Row(
                    modifier = Modifier
                        .padding(top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = invoice.NumberOfPeople.toString(),
                        color = Color.DarkGray,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(end = 6.dp)

                    )

                    Icon(
                        painter = painterResource(R.drawable.user_icon),
                        tint = colorResource(R.color.main_color_bold),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        Column(
            modifier = Modifier.padding(0.dp).fillMaxHeight()
                .background(
                    color = Color.Transparent
                )
        ) {
            Row(
                modifier = Modifier
                    .drawBehind{
                        val xStart = 0f
                        val xEnd = size.width
                        val y = size.height
                        drawLine(
                            color = Color.Gray,
                            start = Offset(xStart, y),
                            end =  Offset(xEnd, y),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
                    .weight(1f)
                    .background(
                        color = Color.Transparent
                    )
                    .clickable{
                        onItemClicked()
                    }
                    .padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = annotatedStringFromListItemName(invoice.ListItemName),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = FormatDisplay.formatNumber(invoice.Amount.toString()),
                        color = Color.Gray
                    )
                }

                Icon(
                    painter = painterResource(R.drawable.ic_arrow_right),
                    contentDescription = null,
                    tint = colorResource(R.color.invoice_item_color),
                    modifier = Modifier.size(24.dp)
                )

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp),
            ) {
                InvoiceButton(
                    icon = painterResource(R.drawable.outline_close_24),
                    tint = Color.Red,
                    label = stringResource(R.string.invoice_item_button_cancel),
                    onClick = {
                        onDeleteClick()
                    },
                    modifier = Modifier.weight(1f)
                )

                InvoiceButton(
                    icon = painterResource(R.drawable.ic_dollar),
                    tint = colorResource(R.color.dollar_icon_color),
                    label = stringResource(R.string.invoice_item_button_payment),
                    onClick = {
                        onPaymentClick()
                    },
                    modifier = Modifier.weight(1f),
                    hasBorder = true
                )
            }
        }
    }
}

@Composable
fun InvoiceButton(
    icon: Painter,
    label: String,
    tint: Color = Color.Unspecified,
    onClick: () -> Unit,
    modifier: Modifier,
    hasBorder: Boolean = false
) {
    Row(
        modifier = modifier
            .background(
                color = colorResource(R.color.invoice_item_color)
            )
            .then(
                if (hasBorder) Modifier.drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    val yStart = 0f
                    val yEnd = size.height
                    val x = 0f
                    drawLine(
                        color = Color.Gray,
                        start = Offset(x, yStart),
                        end = Offset(x, yEnd),
                        strokeWidth = strokeWidth
                    )
                } else Modifier
            )
            .clickable {
                onClick()
            }
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            fontSize = 16.sp
        )
    }
}


@Preview
@Composable
fun InvoiceItemPreview() {
    CukcukTheme {
        InvoiceItem(
            invoice = Invoice(
                TableName = "",
                Amount = 150000.0,
                ListItemName = "Bánh mì ngọt (1)"
            ),
            onDeleteClick = {},
            onPaymentClick = {}
        )
    }
}

fun annotatedStringFromListItemName(itemListName: String): AnnotatedString {
    return buildAnnotatedString {
        val items = itemListName.split(", ")

        items.forEachIndexed { index, item ->
            val regex = Regex("(.+?)\\s*\\((\\d+(\\.\\d+)?)\\)")
            val match = regex.find(item)

            if (match != null) {
                val itemName = match.groupValues[1]
                val quantity = match.groupValues[2]

                withStyle(style = SpanStyle(color = Color.Black, fontSize = 16.sp)) {
                    append(itemName)
                }

                withStyle(style = SpanStyle(color = Color.Blue, fontSize = 14.sp)) {
                    append(" ($quantity)")
                }
            } else {
                append(item)
            }

            if (index != items.lastIndex) {
                append(", ")
            }
        }
    }
}