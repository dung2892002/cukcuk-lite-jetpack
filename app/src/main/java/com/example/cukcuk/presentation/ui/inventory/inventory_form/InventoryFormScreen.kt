package com.example.cukcuk.presentation.ui.inventory.inventory_form

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cukcuk.R
import com.example.cukcuk.presentation.components.CukcukButton
import com.example.cukcuk.presentation.components.CukcukDialog
import com.example.cukcuk.presentation.components.CukcukImageBox
import com.example.cukcuk.presentation.components.CukcukToolbar
import com.example.cukcuk.presentation.ui.calculator.CalculatorDialog
import com.example.cukcuk.utils.FormatDisplay

@Composable
fun InventoryFormScreen(
    navController: NavHostController,
    viewModel: InventoryFormViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    var inventory = viewModel.inventory.value
    val errorMessage = viewModel.errorMessage.value
    val showSelectColor = viewModel.showSelectColor.value
    val showSelectImage = viewModel.showSelectImage.value
    val showDialogDelete = viewModel.isOpenDialogDelete.value
    val showCalculator = viewModel.showCalculator.value
    val isSubmitSuccess = viewModel.isSubmitSuccess.value

    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            viewModel.updateErrorMessage(null)
        }
    }

    LaunchedEffect(isSubmitSuccess) {
        if (isSubmitSuccess) {
            navController.popBackStack()
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

    @Composable
    fun buildDialogContent() : AnnotatedString {
        val first = stringResource(id = R.string.annotate_delete_inventory_first)
        val last = stringResource(id = R.string.annotate_delete_inventory_last)

        return buildAnnotatedString {
            append(first)
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append(" ${inventory.InventoryName} ")
            }
            append(last)
        }
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            CukcukToolbar(
                title = if (inventory.InventoryID == null)
                    stringResource(R.string.toolbar_title_AddInventory)
                else stringResource(R.string.toolbar_title_EditInventory),
                menuTitle =  stringResource(R.string.toolbar_menuTitle_Submit),
                false,
                onBackClick =  {navController.popBackStack()},
                onMenuClick = {viewModel.submit()} )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 20.dp)
                    .background(
                        color = Color.White
                    ),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                if (inventory.InventoryID != null) {
                    CukcukButton(
                        title = stringResource(R.string.button_title_Delete),
                        bgColor = Color.Red,
                        textColor = Color.White,
                        onClick = {
                            viewModel.openDialogDelete()
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
                CukcukButton(
                    title = stringResource(R.string.button_title_Submit),
                    bgColor = colorResource(R.color.main_color),
                    textColor = Color.White,
                    onClick = {
                        viewModel.submit()
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)

        ) {
            FormRow(
                label = stringResource(R.string.form_row_inventory_name_title),
                value = inventory.InventoryName,
                onValueChange = { viewModel.updateInventoryName(it) },
                isRequired = true
            )

            FormRow(
                label = stringResource(R.string.form_row_inventory_price_title),
                value = FormatDisplay.formatNumber(inventory.Price.toString()),
                isRequired = false,
                onClick = {
                    viewModel.openCalculator()
                }
            )

            FormRow(
                label = stringResource(R.string.form_row_inventory_unit_title),
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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    FormRowLabel(
                        label = stringResource(R.string.form_row_inventory_color_title)
                    )

                    CukcukImageBox(
                        color = inventory.Color,
                        imageName = null,
                        iconDrawable = painterResource(R.drawable.ic_colors),
                        onClick = {
                            viewModel.openSelectColor()
                        }
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    FormRowLabel(
                        label = stringResource(R.string.form_row_inventory_icon_title)
                    )

                    CukcukImageBox(
                        color = inventory.Color,
                        imageName = inventory.IconFileName,
                        onClick = {
                            viewModel.openSelectImage()
                        }
                    )
                }
            }

            if (inventory.InventoryID != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    FormRowLabel(
                        label = stringResource(R.string.form_row_inventory_active_title)
                    )

                    Checkbox(
                        checked = !inventory.Inactive,
                        colors = CheckboxDefaults.colors(
                            uncheckedColor = colorResource(R.color.main_color),
                            checkedColor = colorResource(R.color.main_color),
                            checkmarkColor = Color.White
                        ),
                        onCheckedChange = { viewModel.updateInactive(it) }
                    )
                    Text(text = stringResource(R.string.inventory_inactive), fontSize = 16.sp)
                }
            }

        }
    }

    if (showDialogDelete) {
        CukcukDialog(
            title = stringResource(R.string.dialog_content),
            message = buildDialogContent(),
            valueTextField = null,
            onConfirm = {
                viewModel.delete()
            },
            onCancel = {
                viewModel.closeDialogDelete()
            },
            confirmButtonText = stringResource(R.string.button_title_Yes),
            cancelButtonText = stringResource(R.string.button_title_No),
        )
    }

    if (showSelectColor) {
            ColorInventoryForm(
                currentColor = inventory.Color,
                onSelectColor = {
                    viewModel.updateColor(it)
                    viewModel.closeSelectColor()
                },
                onCloseForm = {
                    viewModel.closeSelectColor()
                }
            )
    }

    if (showSelectImage) {
        ImageInventoryForm(
            onSelectImage = {
                viewModel.updateIconFileName(it)
                viewModel.closeSelectImage()
            },
            onCloseForm = {
                viewModel.closeSelectImage()
            }
        )
    }

    if (showCalculator) {
        CalculatorDialog(
            input = inventory.Price.toString(),
            title = stringResource(R.string.calculator_title_inventory_price),
            message = stringResource(R.string.calculator_message_inventory_price),
            maxValue = 999999999.0,
            minValue = 0.0,
            onClose = {
                viewModel.closeCalculator()
            },
            onSubmit = {
                viewModel.updatePrice(it.toDouble())
            },
        )
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
        FormRowLabel(
            label = label,
            isRequired = isRequired
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
                    painter = painterResource(R.drawable.ic_arrow_right),
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


@Composable
fun FormRowLabel(
    label: String,
    isRequired: Boolean = false
) {
    val required = stringResource(R.string.required)
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Gray, fontSize = 14.sp)) {
                append(label)
            }
            if (isRequired) {
                withStyle(style = SpanStyle(color = Color.Red)) {
                    append(" $required")
                }
            }
        },
        modifier = Modifier.padding(horizontal = 10.dp)
    )
}
