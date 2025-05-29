package com.example.cukcuk.presentation.ui.inventory.inventory_form

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.cukcuk.presentation.components.Toolbar
import com.example.cukcuk.utils.FormatDisplay
import java.util.UUID

@Composable
fun InventoryFormScreen(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry,
    viewModel: InventoryFormViewModel = hiltViewModel()
) {

    var inventory = viewModel.inventory.value
    val inventoryId = backStackEntry.arguments?.getString("inventoryId")
    val title = if (inventoryId == null) "Thêm món" else "Sửa món"

    val errorMessage = viewModel.errorMessage.value

    LaunchedEffect(errorMessage) {
        if (errorMessage == null) {
            navController.popBackStack()
        }
    }



    LaunchedEffect(inventoryId) {
        if (inventoryId != null) {
            val inventoryIdUUID = UUID.fromString(inventoryId)
            viewModel.loadInventory(inventoryIdUUID)
        }
    }

    val selectedUnit = navController
        .currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<com.example.cukcuk.domain.model.Unit>("selectedUnit")

    LaunchedEffect(selectedUnit?.value) {
        selectedUnit?.value?.let {
            viewModel.updateUnit(it)
        }
    }

    Scaffold(
        topBar = {
            Toolbar(title,"Cất", false, {navController.popBackStack()}, {viewModel.submit()} )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)

        ) {
            FormRow(
                label = "Tên món",
                value = inventory.InventoryName,
                onValueChange = { viewModel.updateInventoryName(it) },
                isRequired = true
            )

            FormRow(
                label = "Giá bán",
                value = FormatDisplay.formatNumber(inventory.Price.toString()),
                isRequired = false,
                onClick = {
                    print("bat may tinh")
                }
            )

            FormRow(
                label = "Đơn vị tính",
                value = inventory.UnitName,
                isRequired = true,
                onClick = {
                    if (inventory.UnitID == null) {
                        navController.navigate("unit_list")
                    } else {
                        navController.navigate("unit_list?currentUnitId=${inventory.UnitID}")
                    }
                }
            )
        }
    }
}

@Composable
fun FormRow(
    label: String,
    value: String,
    isRequired: Boolean = false,
    onClick: (() -> Unit)? = null,
    onValueChange: ((String) -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = strokeWidth
                )
            }
            .let {
                if (onClick != null) it.clickable { onClick() } else it
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Label + Required marker
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Gray)) {
                    append(label)
                }
                if (isRequired) {
                    withStyle(style = SpanStyle(color = Color.Red)) {
                        append(" *")
                    }
                }
            },
            modifier = Modifier.padding(horizontal = 10.dp)
        )

        if (onValueChange != null) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.End,
                    color = Color.Black,
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        innerTextField()
                    }
                },
                cursorBrush = SolidColor(Color.Black),
                keyboardOptions = KeyboardOptions.Default
            )
        } else {
            Text(
                text = value,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End,
                fontSize = 18.sp,
                color = Color.Black
            )
        }

        Box(modifier = Modifier.width(32.dp)) {
            if (onClick != null) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    tint = Color.Gray,
                    contentDescription = null,
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp)
                )
            }
        }
    }
}

