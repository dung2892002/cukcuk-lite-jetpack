package com.example.cukcuk.presentation.ui.invoice.invoice_payment

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.cukcuk.R
import com.example.cukcuk.domain.model.InvoiceDetail
import com.example.cukcuk.presentation.components.CukcukButton
import com.example.cukcuk.presentation.components.Toolbar
import com.example.cukcuk.presentation.ui.calculator.CalculatorDialog
import com.example.cukcuk.utils.FormatDisplay.formatNumber
import com.example.cukcuk.utils.FormatDisplay.formatTo12HourWithCustomAMPM
import java.util.UUID

@Composable
fun PaymentScreen(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry,
    viewModel: PaymentViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val invoiceIdArgs = backStackEntry.arguments?.getString("invoiceId")
    LaunchedEffect(invoiceIdArgs) {
        viewModel.loadData(UUID.fromString(invoiceIdArgs!!))
    }

    val invoice = viewModel.invoice.value
    val showCalculator = viewModel.showCalculator.value

    fun handlePayment() {
        val response = viewModel.paymentInvoice()
        if (response.isSuccess) {
            navController.navigate("invoice_form") {
                popUpTo("home") { inclusive = false }
                launchSingleTop = true
            }
        } else {
            Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            Toolbar(
                title = "Thu tiền",
                menuTitle = "Xong",
                hasMenuIcon = false,
                onBackClick = {navController.popBackStack()},
                onMenuClick = {
                    handlePayment()
                }
            )
        },
    ) {paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorResource(R.color.background_color)
                )
                .padding(paddingValue)
                .padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White
                    )
                    .padding(start = 6.dp, end = 6.dp, top = 10.dp),
            ) {
                Text(
                    text = "HÓA ĐƠN",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 10.dp).align(Alignment.CenterHorizontally)
                )

                Text(
                    text = "Số: ${invoice.InvoiceNo}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 10.dp).align(Alignment.CenterHorizontally)
                )
                if (invoice.TableName.isNotEmpty()) {
                    Row(
                        modifier = Modifier.padding(bottom = 4.dp)
                    ) {
                        Text(
                            text = "Bàn: ",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.width(60.dp)
                        )
                        Text(
                            text = invoice.TableName,
                            fontSize = 16.sp,
                        )
                    }
                }

                Row(
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    Text(
                        text = "Ngày: ",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(60.dp)
                    )
                    Text(
                        text = formatTo12HourWithCustomAMPM(invoice.InvoiceDate.toString()),
                        fontSize = 16.sp,
                    )
                }


                //danh sach mon an
                TableHeader()
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 0.dp, max = 340.dp)
                        .drawBehind {
                            val borderSize = 1.dp.toPx()
                            drawRect(
                                color = Color.Black,
                                size = size,
                                style = Stroke(width = borderSize)
                            )
                        }
                ) {
                    items(invoice.InvoiceDetails) { item ->
                        InvoiceDetailItem(item = item)
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Text(
                        text = "Số tiền phải trả",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = formatNumber(invoice.Amount.toString()),
                        fontWeight = FontWeight.Bold
                    )
                }

                HorizontalDivider(
                    color = Color.Black,
                    thickness = 1.dp
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable{viewModel.openCalculator()}
                        .padding(vertical = 10.dp)
                ) {
                    Text(
                        text = "Tiền khách đưa",
                        modifier = Modifier.weight(1f),
                        color = colorResource(R.color.main_color)
                    )

                    Text(
                        text = formatNumber(invoice.ReceiveAmount.toString()),
                        color = colorResource(R.color.main_color)
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_right),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }

                HorizontalDivider(
                    color = Color.Black,
                    thickness = 1.dp
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Text(
                        text = "Tiền trả lại cho khách",
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = formatNumber(invoice.ReturnAmount.toString()),
                    )
                }

            }

            Icon(
                painter = painterResource(R.drawable.top_zigzag),
                contentDescription = null,
                tint = Color.White,
            )

            Spacer(
                modifier = Modifier.weight(1f)
            )

            CukcukButton(
                title = "XONG",
                bgColor = colorResource(R.color.main_color),
                textColor = Color.White,
                onClick = {
                    handlePayment()
                },
                padding = 0,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                fontSize = 20
            )
        }
    }

    if (showCalculator) {
        CalculatorDialog(
            input = invoice.ReceiveAmount.toString(),
            title = "Nhập tiền khách đưa",
            message = "Số tiền",
            maxValue = 999999999.0,
            minValue = 0.0,
            onClose = {
                viewModel.closeCalculator()
            },
            onSubmit = {
                viewModel.updateAmount(it.toDouble())
            }
        )
    }
}


@Preview
@Composable
fun TableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White
            )
            .drawBehind {
                val borderSize = 1.dp.toPx()
                drawRect(
                    color = Color.Black,
                    size = size,
                    style = Stroke(width = borderSize)
                )
            }
            .padding(vertical = 10.dp)
    ) {
        Text(
            text = "Tên hàng",
            textAlign = TextAlign.Center,
            maxLines = 2,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(3f)
        )

        Text(
            text = "SL",
            textAlign = TextAlign.Center,
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "Đơn giá",
            textAlign = TextAlign.End,
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(2f)
        )

        Text(
            text = "Thành tiền",
            textAlign = TextAlign.End,
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(3f).padding(end = 6.dp)
        )
    }
}

@Composable
fun InvoiceDetailItem(
    item: InvoiceDetail
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind{
                val strokeWidth = 1.dp.toPx()
                val xStart = 0f
                val xEnd = size.width
                val y = size.height
                drawLine(
                    color = Color.Gray,
                    start = Offset(xStart, y),
                    end = Offset(xEnd, y),
                    strokeWidth = strokeWidth
                )
            }
            .padding(vertical = 10.dp)
    ) {
        Text(
            text = item.InventoryName,
            textAlign = TextAlign.Start,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(3f).padding(start = 6.dp)
        )

        Text(
            text = item.Quantity.toString(),
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = formatNumber(item.UnitPrice.toString()),
            textAlign = TextAlign.End,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(2f)
        )

        Text(
            text = formatNumber(item.Amount.toString()),
            textAlign = TextAlign.End,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(3f).padding(end = 6.dp)
        )
    }
}