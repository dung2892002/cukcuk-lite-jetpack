package com.example.presentation.ui.unit.unit_form

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.presentation.components.CukcukDialog
import java.util.UUID
import com.example.presentation.R

@Composable
fun UnitForm(
    unitId: UUID?,
    onClose: (Boolean) -> Unit,
    viewModel: UnitFormViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(unitId) {
        if (unitId != null) {
            viewModel.fetchUnitDetail(unitId)
        }
    }

    val unit = viewModel.unit.value
    val errorMessage = viewModel.errorMessage.value
    val isShowForm = viewModel.isShowForm.value

    fun closeForm(reload: Boolean) {
        viewModel.resetValue()
        onClose(reload)
    }

    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(isShowForm) {
        if (isShowForm == false) {
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
    unit: com.example.domain.model.Unit,
    onUnitNameChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onCancel: () -> Unit
) {
    CukcukDialog(
        title = if (unit.UnitID == null)
            stringResource(id = R.string.dialog_add_unit_title)
        else stringResource(id = R.string.dialog_edit_unit_title),
        message = null,
        valueTextField = unit.UnitName,
        onConfirm = onSubmit,
        onCancel = onCancel,
        confirmButtonText = stringResource(R.string.button_title_Submit),
        cancelButtonText = stringResource(R.string.button_title_Cancel),
        onValueChange = onUnitNameChange
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewUnitFormContent() {
    MaterialTheme {
        UnitFormContent(
            unit = com.example.domain.model.Unit().copy(
                UnitID = null,
                UnitName = "abc"),
            onUnitNameChange = {},
            onSubmit = {},
            onCancel = {}
        )
    }
}

