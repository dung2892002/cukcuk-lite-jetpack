package com.example.cukcuk.presentation.ui.invoice.invoice_list

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cukcuk.R
import com.example.cukcuk.presentation.components.CukcukDialog

@Composable
fun InvoiceListScreen(
    navController: NavHostController,
    viewModel: InvoiceListViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val invoices = viewModel.invoices.value
    val isShowDeleteDialog = viewModel.showDeleteDialog.value
    val errorMessage = viewModel.errorMessage.value

    LaunchedEffect(Unit) {
        viewModel.loadInvoiceNotPayment()
    }

    LaunchedEffect(errorMessage) {
        if (errorMessage != null)
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    if (invoices.isEmpty()){
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(R.drawable.right_top_focus),
                    contentDescription = null,
                    modifier = Modifier.height(100.dp),
                    tint = Color.DarkGray
                )
            }
            Text(
                text = "Chưa có khách gọi món",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = "Bấm vào đây hoặc dấu + để gọi món",
                color = colorResource(R.color.main_color),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = {
                            navController.navigate("invoice_form")
                        }
                    ),
                textAlign = TextAlign.Center
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = colorResource(R.color.background_color)
                )
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            items(invoices) { invoice ->
                InvoiceItem(
                    invoice = invoice,
                    onDeleteClick = {
                        viewModel.openDialogDelete(invoice)
                    },
                    onPaymentClick = {
                        navController.navigate("payment?invoiceId=${invoice.InvoiceID}")
                    },
                    onItemClicked = {
                        navController.navigate("invoice_form?invoiceId=${invoice.InvoiceID}")
                    }
                )
            }
        }
    }

    if(isShowDeleteDialog) {
        CukcukDialog(
            title = stringResource(R.string.dialog_content),
            valueTextField = null,
            message = buildAnnotatedString {
                append("Bạn có chắc muốn hủy các món đã chọn")
            },
            onConfirm = {
                viewModel.deleteInvoice()
            },
            onCancel = {
                viewModel.closeDialogDelete()
            },
            confirmButtonText = "CÓ",
            cancelButtonText = "KHÔNG"
        )
    }
}

