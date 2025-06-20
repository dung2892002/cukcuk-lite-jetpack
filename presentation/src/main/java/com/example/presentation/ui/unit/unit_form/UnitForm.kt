package com.example.presentation.ui.unit.unit_form

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.presentation.components.CukcukDialog
import java.util.UUID
import com.example.presentation.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun UnitForm(
    unitId: UUID?,
    onClose: (Boolean) -> Unit,
    onLoadingChanged: (Boolean) -> Unit,
    viewModel: UnitFormViewModel = koinViewModel()
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
    val isDataLoaded = viewModel.isDataLoaded.value
    val loading = viewModel.loading.value

    LaunchedEffect(loading) {
        onLoadingChanged(loading)
    }


    fun closeForm(reload: Boolean) {
        viewModel.resetValue()
        onClose(reload)
    }

    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            viewModel.setErrorMessage(null)
        }
    }

    LaunchedEffect(isShowForm) {
        if (isShowForm == false) {
            closeForm(true)
        }
    }

    if (unitId == null || isDataLoaded) {
        CukcukDialog(
            title = if (unit.UnitID == null)
                stringResource(id = R.string.dialog_add_unit_title)
            else stringResource(id = R.string.dialog_edit_unit_title),
            message = null,
            valueTextField = unit.UnitName,
            onConfirm = { viewModel.submitForm(context) },
            onCancel = { closeForm(false) },
            confirmButtonText = stringResource(R.string.button_title_Submit),
            cancelButtonText = stringResource(R.string.button_title_Cancel),
            onValueChange = { viewModel.updateNewUnitName(it) }
        )
    }
}


