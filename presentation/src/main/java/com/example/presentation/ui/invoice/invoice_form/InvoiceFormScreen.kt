package com.example.presentation.ui.invoice.invoice_form

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.presentation.R
import com.example.presentation.components.CukcukButton
import com.example.presentation.components.CukcukImageButton
import com.example.presentation.components.CukcukToolbar
import com.example.presentation.ui.calculator.CalculatorDialog
import com.example.presentation.ui.calculator.IntegerCalculatorDialog
import com.example.domain.utils.FormatDisplay
import kotlinx.coroutines.launch

@Composable
fun InvoiceFormScreen(
    navController: NavHostController,
    viewModel: InvoiceFormViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val invoice = viewModel.invoice.value
    val inventoriesSelect = viewModel.inventoriesSelect.value
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
        coroutineScope.launch {
            val invoiceId = viewModel.submitForm()
            if (invoiceId != null) {
                navController.navigate("payment?invoiceId=${invoiceId}")
            }
        }
    }


    fun submit() {
        coroutineScope.launch {
            val id = viewModel.submitForm()
            if(id != null) {
                navController.popBackStack()
            }
        }
    }

    Scaffold(
        topBar = {
            CukcukToolbar(
                title = stringResource(R.string.toolbar_title_InvoiceForm),
                menuTitle = stringResource(R.string.toolbar_menuTitle_InvoiceForm),
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
                        text = stringResource(R.string.invoice_form_totalAmount),
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
                        title = stringResource(R.string.button_title_Submit),
                        bgColor = Color.White,
                        textColor = colorResource(R.color.main_color),
                        onClick = {
                            submit()
                        },
                        modifier = Modifier.height(40.dp).weight(2f)
                    )

                    CukcukButton(
                        title = stringResource(R.string.button_title_Payment),
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
                        viewModel.updateQuantityInventoryWithIndex(index, item.quantity + 1)
                    },
                    onButtonAddClick = {
                        viewModel.updateQuantityInventoryWithIndex(index, item.quantity + 1)
                    },
                    onButtonSubtractClick = {
                        viewModel.updateQuantityInventoryWithIndex(index,
                            if (item.quantity - 1 > 0) item.quantity - 1 else 0.0)
                    },
                    onButtonRemoveClick = {
                        viewModel.updateQuantityInventoryWithIndex(index, 0.0)
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
            title = stringResource(R.string.calculator_title_invoice_tableName),
            message = stringResource(R.string.calculator_message_invoice_tableName),
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
            title = stringResource(R.string.calculator_title_invoice_numberPeople),
            message = stringResource(R.string.calculator_message_invoice_numberPeople),
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
            input = inventoriesSelect[currentInventoryIndex].quantity.toString(),
            title = stringResource(R.string.calculator_title_invoice_inventoryQuantity),
            message = stringResource(R.string.calculator_message_invoice_inventoryQuantity),
            maxValue = 9999999.0,
            minValue = 0.0,
            onClose = {
                viewModel.closeCalculator()
            },
            onSubmit = {
                viewModel.updateQuantityInventory(it.toDouble())
            }
        )
    }
}