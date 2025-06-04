package com.example.cukcuk.presentation.ui.invoice.invoice_form

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.cukcuk.R
import com.example.cukcuk.presentation.components.CukcukButton
import com.example.cukcuk.presentation.components.CukcukImageButton
import com.example.cukcuk.presentation.components.Toolbar
import com.example.cukcuk.presentation.ui.calculator.CalculatorDialog
import com.example.cukcuk.presentation.ui.calculator.IntegerCalculatorDialog
import com.example.cukcuk.utils.FormatDisplay
import java.util.UUID

@Composable
fun InvoiceFormScreen(
    navController: NavHostController,
    viewModel: InvoiceFormViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val invoice = viewModel.invoice.value
    val inventoriesSelect = viewModel.inventoriesSelect
    val showCalculatorQuantity = viewModel.showCalculatorQuantity.value
    val showCalculatorTableName = viewModel.showCalculatorTableName.value
    val showCalculatorNumberPeople = viewModel.showCalculatorNumberPeople.value
    val currentInventoryIndex = viewModel.currentInventoryIndex.value
    val errorMessage = viewModel.errorMessage.value

    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    fun payment() {
        val invoiceId = viewModel.submitForm()
        if (invoiceId != null) {
            navController.navigate("payment?invoiceId=${invoiceId}")
        }
    }


    fun submit() {
        val id = viewModel.submitForm()
        if(id != null) {
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            Toolbar(
                title = "Chọn món",
                menuTitle = "Thu tiền",
                hasMenuIcon = false,
                onBackClick = {navController.popBackStack()},
                onMenuClick = {
                    payment()
                }
            )
        },

        bottomBar = {
            Column {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .background(
                            color = Color.White
                        )
                )
                Row(
                    modifier = Modifier
                        .background(
                            color = colorResource(R.color.invoice_form_bottom_first)
                        )
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_table),
                        contentDescription = null,
                        tint = colorResource(R.color.main_color),
                        modifier = Modifier.padding(end = 6.dp).size(24.dp)
                    )

                    CukcukButton(
                        title = invoice.TableName,
                        bgColor = Color.White,
                        textColor = Color.Black,
                        onClick = {
                            viewModel.openCalculatorTableName()
                        },
                        padding = 2,
                        modifier = Modifier
                            .height(36.dp)
                            .widthIn(min = 40.dp, max = 80.dp)
                    )

                    Icon(
                        painter = painterResource(R.drawable.user_icon),
                        contentDescription = null,
                        tint = colorResource(R.color.main_color),
                        modifier = Modifier.padding(horizontal = 6.dp).size(24.dp)
                    )

                    CukcukButton(
                        title = if (invoice.NumberOfPeople > 0) invoice.NumberOfPeople.toString() else "",
                        bgColor = Color.White,
                        textColor = Color.Black,
                        onClick = {
                            viewModel.openCalculatorNumberPeople()
                        },
                        padding = 2,
                        modifier = Modifier
                            .height(36.dp)
                            .widthIn(min = 40.dp, max = 80.dp)
                    )

                    Text(
                        text = "Tổng tiền",
                        modifier = Modifier.padding(start = 6.dp),
                        fontSize = 16.sp
                    )

                    Text(
                        text = FormatDisplay.formatNumber(invoice.Amount.toString()),
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End
                    )


                }
                Row(
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .background(
                            color = colorResource(R.color.invoice_form_bottom_last)
                        )
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    CukcukImageButton(
                        title = "",
                        onClick = {
                        },
                        bgColor = Color.White,
                        icon = painterResource(R.drawable.ic_microphone),
                        tint = colorResource(R.color.main_color),
                        modifier = Modifier.size(40.dp)
                    )

                    CukcukButton(
                        title = "CẤT",
                        bgColor = Color.White,
                        textColor = colorResource(R.color.main_color),
                        onClick = {
                            submit()
                        },
                        modifier = Modifier.height(40.dp).weight(2f)
                    )

                    CukcukButton(
                        title = "THU TIỀN",
                        bgColor = colorResource(R.color.main_color),
                        textColor = Color.White,
                        onClick = {
                            payment()
                        },
                        modifier = Modifier.height(40.dp).weight(3f)
                    )
                }
            }
        },
        containerColor = Color.White
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .background(Color.White)
        )
        {
            itemsIndexed(inventoriesSelect)
            { index,item ->
                InventorySelectItem(
                    item = item,
                    onItemClick = {
                        println("click ${item.inventory.InventoryName}")
                        item.quantity.value += 1
                        viewModel.updateAmount(item.inventory.Price)
                    },
                    onButtonAddClick = {
                        item.quantity.value += 1
                        viewModel.updateAmount(item.inventory.Price)
                    },
                    onButtonSubtractClick = {
                        if (item.quantity.value > 0){
                            item.quantity.value -= 1
                            viewModel.updateAmount(-item.inventory.Price)
                        }
                    },
                    onButtonRemoveClick = {
                        viewModel.updateAmount(-item.inventory.Price * item.quantity.value)
                        item.quantity.value = 0.0
                    },
                    onCalculatorClick = {
                        viewModel.openCalculatorQuantity(index)
                    }
                )
            }
        }
    }

    if (showCalculatorTableName) {
        IntegerCalculatorDialog(
            input = invoice.TableName,
            title = "Nhập số bàn",
            message = "Số bàn",
            maxValue = 9999.0,
            minValue = 0.0,
            onClose = {
                viewModel.closeCalculator()
            },
            onSubmit = {
                viewModel.updateNewTable(it)
            }
        )
    }

    if (showCalculatorNumberPeople) {
        IntegerCalculatorDialog(
            input = invoice.NumberOfPeople.toString(),
            title = "Nhập số người",
            message = "Số người",
            maxValue = 9999.0,
            minValue = 0.0,
            onClose = {
                viewModel.closeCalculator()
            },
            onSubmit = {
                println(it)
                viewModel.updateNewNumberPeople(it.toDouble().toInt())
            }
        )
    }

    if (showCalculatorQuantity) {
        CalculatorDialog(
            input = inventoriesSelect[currentInventoryIndex].quantity.value.toString(),
            title = "Nhập số lượng",
            message = "Số lượng",
            maxValue = 9999999.0,
            minValue = 0.0,
            onClose = {
                viewModel.closeCalculator()
            },
            onSubmit = {
                viewModel.updateNewQuantity(it.toDouble())
            }
        )
    }
}