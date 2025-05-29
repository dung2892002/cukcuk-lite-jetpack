package com.example.cukcuk.presentation.ui.unit.unit_form

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cukcuk.presentation.components.CukcukDialog
import java.util.UUID

@Composable
fun UnitForm(
    unitId: UUID?,
    onClose: (Boolean) -> Unit,
    viewModel: UnitFormViewModel = hiltViewModel()
) {

    LaunchedEffect(unitId) {
        if (unitId != null) {
            viewModel.fetchUnitDetail(unitId)
        }
    }

    val unit = viewModel.unit.value
    val errorMessage = viewModel.errorMessage.value

    fun closeForm(reload: Boolean) {
        viewModel.resetValue()
        onClose(reload)
    }

    LaunchedEffect(errorMessage) {
        if (errorMessage == null) {
            closeForm(true)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        UnitFormContent(
            unit = unit,
            onUnitNameChange = { viewModel.updateNewUnitName(it) },
            onSubmit = { viewModel.submitForm() },
            onCancel = { closeForm(false) },
        )
    }
}

@Composable
fun UnitFormContent(
    unit: com.example.cukcuk.domain.model.Unit,
    onUnitNameChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onCancel: () -> Unit
) {
    CukcukDialog(
        title = if (unit.UnitID == null) "Thêm đơn vị tính" else "Sửa đơn vị tính",
        message = unit.UnitName,
        onConfirm = onSubmit,
        onCancel = onCancel,
        confirmButtonText = "CẤT",
        cancelButtonText = "HỦY BỎ",
        onValueChange = onUnitNameChange
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewUnitFormContent() {
    MaterialTheme {
        UnitFormContent(
            unit = com.example.cukcuk.domain.model.Unit().copy(
                UnitID = null,
                UnitName = "abc"),
            onUnitNameChange = {},
            onSubmit = {},
            onCancel = {}
        )
    }
}

